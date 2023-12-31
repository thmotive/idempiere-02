/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Carlos Ruiz                                           *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Carlos Ruiz - globalqss                                           *
***********************************************************************/

package org.idempiere.fitnesse.fixture;

import java.util.Properties;

import fitnesse.fixtures.TableFixture;

/**
 *	iDempiere Assert String fixture for use with fitnesse framework testing
 *	@red1 String values only
 *  @author Carlos Ruiz - globalqss
 */
public class AssertString extends TableFixture {
	private volatile static Instance adempiereInstance = null;
	
	@Override
	protected void doStaticTable(int rows) {
		if (adempiereInstance == null) {
			adempiereInstance = Static_iDempiereInstance.getInstance();
		}
		if (adempiereInstance.getAdempiereService() == null || ! adempiereInstance.getAdempiereService().isLoggedIn()) {
			wrong(rows-1, 1);
			getCell(rows-1, 1).addToBody("not logged in");
			return;
		}
		Properties ctx = adempiereInstance.getAdempiereService().getCtx();
		int windowNo = adempiereInstance.getAdempiereService().getWindowNo();
		String trxName = adempiereInstance.getAdempiereService().get_TrxName();
		
		for (int i = 0; i < rows; i++) {
			String cell_title = getText(i, 0);
			String title_evaluated = cell_title;
			if (cell_title.startsWith("@")) {
				title_evaluated = Util.evaluate(ctx, windowNo, cell_title, getCell(i, 0),trxName);
			}
			
			String cell_value = getText(i, 1);
			String value_evaluated = cell_value;
			if (cell_value.startsWith("@")) {
				value_evaluated = Util.evaluate(ctx, windowNo, cell_value, getCell(i, 1),trxName);
			}
			
			if (title_evaluated.equals(value_evaluated)) 
				right(i, 1);
			else
				wrong(i, 1); 
			
		}
	} // doStaticTable
	 
} // AdempiereString