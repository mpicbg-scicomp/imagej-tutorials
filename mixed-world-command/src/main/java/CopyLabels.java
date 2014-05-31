/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import ij.IJ;
import ij.ImagePlus;
import net.imagej.ImageJ;

import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/** A command that preserves the labels from one image to another. */
@Plugin(type = Command.class)
public class CopyLabels extends ContextCommand {

	/** Image with desired labels. */
	@Parameter
	private ImagePlus source;

	/** Image to which labels should be assigned. */
	@Parameter
	private ImagePlus target;

	@Override
	public void run() {
		final int sourceSize = source.getStackSize();
		final int targetSize = target.getStackSize();
		if (sourceSize != targetSize) {
			cancel("Source and target images must have the same number of slices." +
				"(" + sourceSize + " != " + targetSize +")");
			return;
		}
		for (int i=1; i<=sourceSize; i++) {
			final String label = source.getStack().getSliceLabel(i);
			target.getStack().setSliceLabel(label, i);
		}
	}

	/** A {@code main()} method for testing. */
	public static void main(final String... args) {
		// Launch ImageJ as usual.
		final ImageJ ij = net.imagej.Main.launch(args);

		ij.ui().showUI();

		IJ.newImage("Fancy", "8-bit", 128, 128, 1);
		IJ.newImage("Simple", "8-bit", 128, 128, 1);

		// Test our "CopyLabels" command.
		ij.command().run(CopyLabels.class, true);
	}

}
