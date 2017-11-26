package com.jeasier.generator.repository;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import com.jeasier.util.EasyJavaProperties;
import com.jeasier.util.EasyJavaUtil;
import com.jeasier.util.FieldUtil;
import com.jeasier.util.IOUtil;


/**
 * 
 * @author João Vitor Feitosa
 * @since 0.0.1
 */

public class EntityHelperGenerator {

	private EasyJavaProperties prop;

	public EntityHelperGenerator(EasyJavaProperties properties) {
		this.prop = properties;
	}

	public static final String TEMPLATE = "/templates/repository/Helper.txt";

	public String generateContent(Class<?> gClass) throws URISyntaxException {
		prop.getProp().setProperty("entity", gClass.getSimpleName());

		StringBuilder template = new StringBuilder(
				IOUtil.lerArquivo(Class.class.getResource(TEMPLATE).getFile()));
		
		//helper
		FieldUtil.replaceAll(template, "${packageHelper}",
				FieldUtil.getFieldFromClass(prop.getProp().getProperty("packageHelper")));
		FieldUtil.replaceAll(template, "${helper}", prop.getProp().getProperty("helper"));
		
		//imports
		FieldUtil.replaceAll(template, "${packageFilter}",
				prop.getProp().getProperty("packageFilter") + "." + prop.getProp().getProperty("filter"));
		FieldUtil.replaceAll(template, "${packageEntity}",
				prop.getProp().getProperty("packageEntity") + "." + prop.getProp().getProperty("entity"));
		
		//classes
		FieldUtil.replaceAll(template, "${entity}", prop.getProp().getProperty("entity"));
		FieldUtil.replaceAll(template, "${filter}", prop.getProp().getProperty("filter"));
		FieldUtil.replaceAll(template, "${filterField}",
				FieldUtil.getFieldFromClass(prop.getProp().getProperty("filter")));

		return template.toString();
	}

	public void generateClass(Class<?> gClass) throws FileNotFoundException, URISyntaxException {

		String pathToSave = EasyJavaUtil.getPathFile(gClass)
				+ EasyJavaUtil.getPathFromPackage(prop.getProp().getProperty("packageHelper"));
		String fileName = prop.getProp().getProperty("helper") + ".java";

		System.out.println("Path " + pathToSave);
		System.out.println("Nome arquivo " + fileName);
		IOUtil.criarPastasCasoNaoExista(pathToSave);
		IOUtil.gravarArquivo(generateContent(gClass), pathToSave, fileName);
	}

}
