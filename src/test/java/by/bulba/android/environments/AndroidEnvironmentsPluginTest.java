/*
 * Copyright (C) 2019. Ilya Pavlovskii
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.bulba.android.environments;

import by.bulba.android.environments.config.ApplicationVariantConfigReaderFactory;
import by.bulba.android.environments.config.ApplicationVariantConfigValueReader;
import by.bulba.android.environments.config.ConfigType;
import by.bulba.android.environments.config.ConfigValue;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.internal.dsl.BuildType;
import org.gradle.api.Action;
import org.gradle.api.DomainObjectSet;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.Collections;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


public class AndroidEnvironmentsPluginTest {

    private static final String ROOT_PATH = "some/root/dir";
    private static final String CONFIG_PATH = "config/dir";

    private static final String KEY = "key";
    private static final String VALUE = "val";
    private static final ConfigType CONFIG_TYPE = ConfigType.STRING;

    private ConfigValue configValue = new ConfigValue.Builder()
            .key(KEY)
            .type(CONFIG_TYPE)
            .value(VALUE)
            .build();

    @Mock
    private File rootFile;
    @Mock
    private ApplicationVariantConfigValueReader configReader;
    @Mock
    private ApplicationVariantConfigReaderFactory configReaderFactory;
    @Mock
    private BuildType buildType;
    @Mock
    private NamedDomainObjectContainer<BuildType> buildTypes;
    private Consumer<BuildType> buildTypeConsumer;

    @Mock
    private ApplicationVariant applicationVariant;
    @Mock
    private DomainObjectSet<ApplicationVariant> applicationVariants;
    private Consumer<ApplicationVariant> appVariantConsumer;
    @Mock
    private AppExtension appExtension;

    @Mock
    private ExtensionContainer extensionContainer;
    private Action<Project> afterEvaluateAction;
    @Mock
    private Project project;

    private AndroidEnvironmentsPlugin plugin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        plugin = new AndroidEnvironmentsPlugin();

        plugin.ext = new AndroidEnvironmentsExtension();
        plugin.ext.configPath = CONFIG_PATH;
        plugin.configReaderFactory = configReaderFactory;
        when(rootFile.getPath()).thenReturn(ROOT_PATH);
        when(project.getRootDir()).thenReturn(rootFile);
        when(extensionContainer.getByName(eq("android"))).thenReturn(appExtension);


        when(configReader.getConfigValues(anyString())).thenReturn(Collections.singleton(configValue));
        when(configReaderFactory.create(
                eq(project),
                any())
        ).thenReturn(configReader);

        when(project.getExtensions()).thenReturn(extensionContainer);

        when(buildType.getName()).thenReturn("some_name");
        doAnswer(invocation ->
                buildTypeConsumer = invocation.getArgument(0)
        ).when(buildTypes).forEach(any());
        when(appExtension.getBuildTypes()).thenReturn(buildTypes);

        when(applicationVariant.getFlavorName()).thenReturn("some_flavor_name");
        doAnswer(invocation ->
                appVariantConsumer = invocation.getArgument(0)
        ).when(applicationVariants).forEach(any());
        when(appExtension.getApplicationVariants()).thenReturn(applicationVariants);

        doAnswer(invocation ->
                afterEvaluateAction = invocation.getArgument(0)
        ).when(project).afterEvaluate(any(Action.class));
    }

    @Test
    public void existsPluginExtension() {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("by.bulba.android.environments");

        project.getExtensions().findByName(AndroidEnvironmentsExtension.EXTENSIONS_NAME);
    }

    @Test
    public void buildConfigFieldOnBuildTypeWhenConfigValuesIsNotEmpty() {
        plugin.processBuildTypes(configReader, appExtension);
        buildTypeConsumer.accept(buildType);

        verify(buildType).buildConfigField(
                eq(CONFIG_TYPE.getConfigString()),
                eq(KEY),
                eq(VALUE)
        );
    }

    @Test
    public void buildConfigFiledOnApplicationVariantWhenConfigValuesIsNotEmpty() {
        plugin.processApplicationVariants(configReader, appExtension);
        appVariantConsumer.accept(applicationVariant);

        verify(applicationVariant).buildConfigField(
                eq(CONFIG_TYPE.getConfigString()),
                eq(KEY),
                eq(VALUE)
        );
    }

    @Test
    public void createExtensionWhenApplyPluginOnProject() {
        plugin.apply(project);

        verify(extensionContainer).create(
                eq(AndroidEnvironmentsExtension.EXTENSIONS_NAME),
                eq(AndroidEnvironmentsExtension.class));
    }

    @Test
    public void callAfterEvaluateSetupWhenApplyPlugin() {
        plugin.apply(project);
        verify(project).afterEvaluate(any(Action.class));
    }

    @Test
    public void setupBuildTypesWhenExtensionHasUseBuildTypes() {
        plugin.ext.useBuildTypes = true;
        plugin.executeTask(project);
        verify(appExtension).getBuildTypes();
    }

    @Test
    public void doNotSetupBuildTypesWhenExtensionHasNotUseBuildTypes() {
        plugin.ext.useBuildTypes = false;
        plugin.executeTask(project);
        verify(appExtension, never()).getBuildTypes();
    }

    @Test
    public void setupAppVariantsWhenExtensionHasUseProductFlavors() {
        plugin.ext.useProductFlavors = true;
        plugin.executeTask(project);
        verify(appExtension).getApplicationVariants();
    }

    @Test
    public void doNotSetupAppVariantsWhenExtensionHasNotUseProductFlavors() {
        plugin.ext.useProductFlavors = false;
        plugin.executeTask(project);
        verify(appExtension, never()).getApplicationVariants();
    }

    @Test
    public void readExtensionAfterEvaluate() {
        plugin.apply(project);
        afterEvaluateAction.execute(project);
        verify(extensionContainer).findByType(eq(AndroidEnvironmentsExtension.class));
    }

}
