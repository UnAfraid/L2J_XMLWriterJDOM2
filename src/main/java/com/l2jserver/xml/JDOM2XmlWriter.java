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

import javax.xml.ws.ServiceMode;

/**
 * @author UnAfraid
 */
@ServiceMode
public class JDOM2XmlWriter implements IXMLWriterProvider
{
	@Override
	public String getName()
	{
		return getClass().getSimpleName();
	}
	
	@Override
	public String getDescription()
	{
		return "An XML Writer based on native JDOM2 Library";
	}
	
	@Override
	public String getAuthor()
	{
		return "UnAfraid";
	}
	
	@Override
	public String getVersion()
	{
		return "1.0.0";
	}
	
	@Override
	public AbstractXMLWriter newWriter()
	{
		return new JDOM2XmlWriterImpl();
	}
}
