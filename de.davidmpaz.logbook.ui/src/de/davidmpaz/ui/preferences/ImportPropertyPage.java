package de.davidmpaz.ui.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;

import de.davidmpaz.importer.Properties;

public class ImportPropertyPage extends AbstractPreferencePage {

	private static final String ISSUES_TITLE = "Issues File path:";
	private static final String TIME_TITLE = "Time entries File path:";

	@Inject private IPreferenceStoreAccess preferenceStoreAccess;

	public ImportPropertyPage() {
		super();
	}

	@Override
	protected void createFieldEditors() {
		FieldEditor issues = new FileFieldEditor("PATH", "&" + ISSUES_TITLE, getFieldEditorParent());
		issues.setPreferenceStore(preferenceStoreAccess.getWritablePreferenceStore());
		issues.setPreferenceName(Properties.PROPERTY_ISSUES_FILE);
		addField(issues);

		FieldEditor time = new FileFieldEditor("PATH", "&" + TIME_TITLE, getFieldEditorParent());
		time.setPreferenceName(Properties.PROPERTY_TIME_FILE);
		time.setPreferenceStore(preferenceStoreAccess.getWritablePreferenceStore());
		addField(time);
	}

	@Override
	public void init(IWorkbench workbench) {
		super.init(workbench);
		setDescription("Importer configurations");
	}

}