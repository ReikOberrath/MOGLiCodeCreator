@TargetFileName ${classDescriptor.simpleName}BuilderUnitTest.java # Name of output file with extension but without path
@TargetDir $model.getMetaInfoValueFor("eclipseProjectDir")/$model.getMetaInfoValueFor("projectName")/src/test/java/<package>
@CreateNew true # creates target dir if not existing and overwrites target file if existing
@NameOfValidModel MOGLiCC_JavaBeanModel

package ${classDescriptor.package}.builder;
'
import static org.junit.Assert.*;
'
import org.junit.*;
'
import java.util.*;
'
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;
'
import ${classDescriptor.package}.*;
import ${classDescriptor.package}.builder.*;
import ${classDescriptor.package}.factory.*;
import ${classDescriptor.package}.utils.*;
'
#parse("commonSubtemplates/importDomainModelClasses.tpl")
'
/**
 * Tests basic functionality of the '${classDescriptor.simpleName}Builder.'
 *
 * @author generated by MOGLiCC
 */
public class ${classDescriptor.simpleName}BuilderUnitTest {
'
'	private final static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("$model.getMetaInfoValueFor("dateTimeFormat")");
'

#parse("C_buildEmptyInstanceMethod.tpl")

'

#parse("D_buildExampleInstanceMethod.tpl")

'

#foreach($attributeDescriptor in $classDescriptor.attributeDescriptorList)

	'
	#set( $AttributeName = $TemplateStringUtility.firstToUpperCase($attributeDescriptor.name) )
	#set( $javaType =  $attributeDescriptor.getMetaInfoValueFor("JavaType") )
	#set( $ExampleData = $attributeDescriptor.getMetaInfoValueFor("ExampleData") )
	
	'	@Test
	'	public void usesCloneWith$AttributeName() {
	'		${classDescriptor.simpleName}Builder builder1 = new ${classDescriptor.simpleName}Builder();
	
			#parse("commonSubtemplates/isJavaTypeDomainObject.tpl")
			
			#if ( $isJavaTypeDomainObject.equals( "true" ) )

				'		final $javaType instance${javaType} = ${javaType}Factory.getById("$ExampleData"); 
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( instance${javaType} );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( instance${javaType} );
			
			#elseif ( $javaType == "String[]" )

				'		final String[] strArr${AttributeName} = CollectionsStringUtils.commaSeparatedStringToStringArray( "$ExampleData" );
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( strArr${AttributeName} );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( strArr${AttributeName} );
			
			#elseif ( $javaType == "java.util.HashSet<String>" )
			
				'		final HashSet<String> strHashSet${AttributeName} = CollectionsStringUtils.commaSeparatedStringToHashSet( "$ExampleData" );
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( strHashSet${AttributeName} );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( strHashSet${AttributeName} );
						
			#elseif ( $javaType == "java.util.List<String>" )
			
				'		final List<String> strList${AttributeName} = CollectionsStringUtils.commaSeparatedStringToStringList( "$ExampleData" );
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( strList${AttributeName} );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( strList${AttributeName} );
						
			#elseif ( $javaType == "java.util.List<Long>" )
			
				'		final List<Long> list${AttributeName} = CollectionsStringUtils.commaSeparatedStringToLongList( "$ExampleData" );
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( list${AttributeName} );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( list${AttributeName} );
						
			#elseif ( $javaType == "String" )
			
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}("$ExampleData");
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}("$ExampleData");

			#elseif ( $javaType == "byte" )
			
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}((byte) $ExampleData);
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}((byte) $ExampleData);
			
			#elseif ( $javaType == "char" )
			
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}('$ExampleData');
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}('$ExampleData');
				
			#elseif ( $javaType == "float" )
				
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}(${ExampleData}F);
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}(${ExampleData}F);
			
			#elseif ( $javaType == "Byte" )
			
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( new Byte("$ExampleData") );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( new Byte("$ExampleData") );
			
			#elseif ( $javaType == "Character" )
			
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( new Character('$ExampleData') );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( new Character('$ExampleData') );
				
			#elseif ( $javaType == "Float" )
				
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( new Float(${ExampleData}F) );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( new Float(${ExampleData}F) );
			
			#elseif ( $javaType == "Long" )
				
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( new Long(${ExampleData}) );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( new Long(${ExampleData}) );
				
			#elseif ( $javaType == "java.math.BigDecimal" || $javaType == "BigDecimal" )
				
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( new BigDecimal("" + ${ExampleData}) );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( new BigDecimal("" + ${ExampleData}) );
			
			#elseif ( $javaType == "org.joda.time.DateTime" || $javaType == "DateTime" ) 
				
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}( dateTimeFormatter.parseDateTime( "${ExampleData}" ) );
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}( dateTimeFormatter.parseDateTime( "${ExampleData}" ) );
				
			#else
			
				'		${classDescriptor.simpleName}Builder builder2 = builder1.cloneWith${AttributeName}($ExampleData);
				'		${classDescriptor.simpleName}Builder builder3 = builder1.with${AttributeName}($ExampleData);
		
			#end			
	
	'		assertFalse("clone expected", builder1.equals(builder2));
	'		assertTrue("no clone expected", builder1.equals(builder3));
	'	}
	'
	
#end
}