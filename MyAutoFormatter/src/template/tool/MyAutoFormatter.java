/**
 * you can put a one sentence description of your tool here.
 *
 * (c) 2015
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   Your Name http://yoururl.com
 * @modified 12/26/2020
 * @version  1.0.0
 */

package template.tool;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class MyAutoFormatter implements Tool, KeyListener {
	Base base;
	Editor editor;

	public String getMenuTitle() {
		return "My Auto Formatter";
	}

	public void init(Base base) {
		// Store a reference to the Processing application itself
		this.base = base;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_H && e.getModifiers() == InputEvent.CTRL_MASK) {
			e.consume();
			int oldEditorLength = editor.getText().length();
			Formater formater = new Formater();
			formater.format(editor.getText());
			int carepos = editor.getCaretOffset();
			int scrollPosition = editor.getScrollPosition();
			editor.setText(formater.getText());
			int newEditorLength = editor.getText().length();

			editor.getTextArea().setCaretPosition(carepos + newEditorLength - oldEditorLength);
			editor.getTextArea().setScrollPosition(scrollPosition);

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void run() {
		// Run this Tool on the currently active Editor window
		System.out.println("My Auto Formatter is running.");
		editor = base.getActiveEditor();
		editor.getTextArea().addKeyListener(this);

	}

}
