/*
 * generated by Xtext 2.29.0
 */
package de.davidmpaz.ui.wizard


import org.eclipse.xtext.ui.wizard.template.FileTemplate
import org.eclipse.xtext.ui.wizard.template.IFileGenerator
import org.eclipse.xtext.ui.wizard.template.IFileTemplateProvider

/**
 * Create a list with all file templates to be shown in the template new file wizard.
 * 
 * Each template is able to generate one or more files.
 */
class LogBookFileTemplateProvider implements IFileTemplateProvider {
	override getFileTemplates() {
		#[new HelloWorldFile]
	}
}

@FileTemplate(label="Hello World", icon="file_template.png", description="Create a hello world for LogBook.")
final class HelloWorldFile {
	val helloName = combo("Hello Name:", #["Xtext", "World", "Foo", "Bar"], "The name to say 'Hello' to")

	override generateFiles(IFileGenerator generator) {
		generator.generate('''«folder»/«name».logtime''', '''
			/*
			 * This is an example model
			 */
			Hello «helloName»!
		''')
	}
}
