/*******************************************************************************
 * Copyright 2013 NEGU Soft
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.negusoft.holoaccent;

import com.negusoft.holoaccent.dialog.DividerPainter;

import android.content.Context;
import android.content.res.Resources;
import android.view.Window;

/**
 * Helper class to lazily initialize AccentResources from your activities. 
 * Simply add the following code to your activity in order to replace the 
 * default android Resources implementation by AccentResources:
 * <pre><code>
private final AccentHelper mAccentHelper = new AccentHelper();
{@literal @}Override
public Resources getResources() {
	return mAccentHelper.getResources(this, super.getResources());
}
 * </code></pre>
 * And don't forget to add the corresponding imports:
 * <pre><code>
import com.negusoft.holoaccent.AccentHelper;
import android.content.res.Resources;
 * </code></pre>
 * 
 * In addition, it allows to easily prepare dialogs for display by calling: 
 * prepareDialog().
 */
public class AccentHelper {
	
	private AccentResources mAccentResources;
	private boolean initializingFlag = false;

	private DividerPainter mDividerPainter;
	
	private final boolean mOverrideThemeColor;
	private final int mOverrideColor;
	
	public AccentHelper() {
		mOverrideThemeColor = false;
		mOverrideColor = 0;
	}
	
	public AccentHelper(int color) {
		mOverrideThemeColor = true;
		mOverrideColor = color;
	}
	
	/** @return The AccentResources instance, properly initialized. */
	public Resources getResources(Context c, Resources resources) {
		if (mAccentResources == null) {
			if (initializingFlag)
				return resources;
			
			initializingFlag = true;
			mAccentResources = createInstance(c, resources);
		}
		return mAccentResources;
	}
	
	/** Paint the dialog's divider if required to correctly customize it. */
	public void prepareDialog(Context c, Window window) {
		if (mDividerPainter == null)
			mDividerPainter = new DividerPainter(c);
		mDividerPainter.paint(window);
	}
	
	private AccentResources createInstance(Context c, Resources resources) {
		if (mOverrideThemeColor) {
			return new AccentResources(c, resources.getAssets(), resources.getDisplayMetrics(),
					resources.getConfiguration(), mOverrideColor);
		}
		else {
			return new AccentResources(c, resources.getAssets(), 
					resources.getDisplayMetrics(), resources.getConfiguration());
		}
	}

}
