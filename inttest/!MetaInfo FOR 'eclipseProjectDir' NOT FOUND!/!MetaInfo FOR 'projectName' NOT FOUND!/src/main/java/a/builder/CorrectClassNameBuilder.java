package a.builder;

import a.CorrectClassName;
import a.validator.CorrectClassNameValidator;
/**
* Builder class of the MOGLiCC JavaBean Group for JavaBean 'CorrectClassName.java'
*
* @author generated by MOGLiCC
*/
public class CorrectClassNameBuilder {

	private CorrectClassName toBuild;

	public CorrectClassNameBuilder(final CorrectClassName data) {
		this.toBuild = cloneCorrectClassName(data);
	}

	public CorrectClassNameBuilder() {
		this.toBuild = new CorrectClassName();
	}

	public CorrectClassName build() {
		return cloneCorrectClassName(toBuild);
	}

	public CorrectClassName buildAndValidate() {
		final CorrectClassName toReturn = cloneCorrectClassName(toBuild);
		try {
			CorrectClassNameValidator.doYourJob(toReturn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}



	public CorrectClassName cloneCorrectClassName(final CorrectClassName object) {
		// Alternatively use clone method generated for BuildUpClassDescriptor [classname=a.CorrectClassName, attributeNumber=0, metaInfoList=]
		final CorrectClassName toReturn = new CorrectClassName();
		return toReturn;
	}
}