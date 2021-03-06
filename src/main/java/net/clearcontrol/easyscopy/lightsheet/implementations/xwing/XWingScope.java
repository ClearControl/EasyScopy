package net.clearcontrol.easyscopy.lightsheet.implementations.xwing;

import clearcontrol.microscope.lightsheet.imaging.DirectFusedImageStack;
import net.clearcontrol.easyscopy.EasyScope;
import net.clearcontrol.easyscopy.lightsheet.EasyLightsheetMicroscope;
import xwing.XWingMicroscope;

/**
 * Author: Robert Haase (http://haesleinhuepf.net) at MPI CBG (http://mpi-cbg.de)
 * February 2018
 */

@EasyScope
public class XWingScope extends EasyLightsheetMicroscope
{

  public static boolean sUseStages = true;

  private static XWingScope sInstance = null;
  public static XWingScope getInstance() {
    if (sInstance == null) {
      sInstance = new XWingScope();
    }
    return sInstance;
  }

  private XWingMicroscope mXWingMicroscope;
  private XWingScope() {
    super(new XWingBuilder(false, sUseStages).getXWingMicroscope());
    mXWingMicroscope = (XWingMicroscope) getLightSheetMicroscope();
  }

  public static void cleanup() {
    if (sInstance != null) {
      sInstance.terminate();
      sInstance = null;
    }
  }
}
