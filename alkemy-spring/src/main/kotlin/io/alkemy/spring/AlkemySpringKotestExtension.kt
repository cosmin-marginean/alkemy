package io.alkemy.spring

import io.alkemy.AlkemyContext
import io.kotest.core.annotation.AutoScan
import io.kotest.core.extensions.PostInstantiationExtension
import io.kotest.core.spec.Spec
import org.springframework.beans.factory.getBeanProvider
import org.springframework.test.context.TestContextManager

@AutoScan
object AlkemySpringKotestExtension : PostInstantiationExtension {
    override suspend fun instantiated(spec: Spec): Spec {
        val manager = TestContextManager(spec::class.java)
        val applicationContext = manager.testContext.applicationContext
        applicationContext.getBeanProvider<AlkemyContext>()
            .ifAvailable
            ?.let { alkemyContext -> spec.extensions(alkemyContext.report) }
        return spec
    }
}
