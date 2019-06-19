package by.bulba.android.environments

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertNotNull

class AndroidEnvironmentsPluginTest {

    @Test
    @Ignore
    public void existsTestTask() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'by.bulba.android.environments'

        assertNotNull(project.tasks.androidEnvTest)
    }
}