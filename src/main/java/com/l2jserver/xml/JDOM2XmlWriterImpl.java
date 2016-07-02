/*
 * Copyright (C) 2004-2014 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jserver.xml;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.xml.transform.Transformer;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.transform.JDOMResult;
import org.jdom2.transform.JDOMSource;

/**
 * @author UnAfraid
 */
public class JDOM2XmlWriterImpl extends AbstractXMLWriter
{
	@Override
	public void processDocument(File dest, XMLDocument xDoc) throws Exception
	{
		final DOMBuilder jdomBuilder = new DOMBuilder();
		final Document doc = jdomBuilder.build(XMLFactory.newDocument());
		
		// Build the document
		processElements(doc, null, xDoc.getEntries());
		
		final JDOMResult out = new JDOMResult();
		
		// Transformer
		final Transformer transformer = XMLFactory.newTransformer();
		transformer.transform(new JDOMSource(doc), out);
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.getFormat().setIndent("\t");
		outputter.outputString(out.getDocument());
		Files.write(dest.toPath(), outputter.outputString(out.getDocument()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
	}
	
	public void processElements(Document doc, Element node, List<XMLElement> entries)
	{
		for (XMLElement entry : entries)
		{
			final Element element = new Element(entry.getName());
			
			// Create attributes
			for (XMLAttribute attr : entry.getAttributes())
			{
				element.setAttribute(new Attribute(attr.getName(), attr.getValue()));
			}
			
			// Create text if there is
			if (entry.getValue() != null)
			{
				element.addContent(entry.getValue());
			}
			
			// Append to the node or document
			if (node == null)
			{
				doc.addContent(element);
			}
			else
			{
				node.addContent(element);
			}
			
			// Process other elements
			processElements(doc, element, entry.getEntries());
		}
	}
}
