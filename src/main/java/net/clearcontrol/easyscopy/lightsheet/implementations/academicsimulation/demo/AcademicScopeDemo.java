package net.clearcontrol.easyscopy.lightsheet.implementations.academicsimulation.demo;

import clearcl.imagej.ClearCLIJ;
import clearcontrol.devices.lasers.LaserDeviceInterface;
import clearcontrol.microscope.lightsheet.imaging.DirectImage;
import clearcontrol.microscope.lightsheet.imaging.SingleViewPlaneImager;
import clearcontrol.stack.OffHeapPlanarStack;
import clearcontrol.stack.StackInterface;
import ij.IJ;
import ij.ImageJ;
import net.clearcontrol.easyscopy.EasyScopyUtilities;
import net.clearcontrol.easyscopy.lightsheet.EasyLightsheetMicroscope;
import net.clearcontrol.easyscopy.lightsheet.implementations.academicsimulation.AcademicScope;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.integer.UnsignedShortType;

/**
 * In the academic scope demo you learn how to assemle a microscope by
 * adding devices to an empty scope. The order how devices are added is
 * decisive
 *
 * Author: Robert Haase (http://haesleinhuepf.net) at MPI CBG (http://mpi-cbg.de)
 * February 2018
 */
public class AcademicScopeDemo
{
  public static void main(String... args) {
    new ImageJ();
    ClearCLIJ clij = ClearCLIJ.getInstance("HD");

    EasyLightsheetMicroscope lScope;

    // setup a scope by adding devices
    {
      AcademicScope lAcademicScope = new AcademicScope();

      lAcademicScope.addLaser(488);
      lAcademicScope.addLaser(592);

      lAcademicScope.addCamera(2048, 2048);

      lAcademicScope.addLightSheet();
      lAcademicScope.addLightSheet();

      lAcademicScope.addOpticalSwitch();
      lAcademicScope.addSignalGenerator();
      lAcademicScope.addAcquisitionStateManager(3);

      // mount a sample and
      lAcademicScope.mountDrosophilaSample(11);
      //lAcademicScope.mountOrganoidSample(11);

      lAcademicScope.turnOn();

      // From here, lScope is just a normal EasyLightSheetMicroscope
      lScope = lAcademicScope;
    }

    System.out.println("The academic scope consists of these devices:");
    for (Object lDevice : lScope.getDevices())
    {
      System.out.println(" * " + lDevice.toString());
    }


    // Turn on a laser
    LaserDeviceInterface
        lLaser = (LaserDeviceInterface) lScope.getDevice("Laser", "488");

    //lScope.getLightSheetMicroscope().getLightSheet(0).getHeightVariable().set(0);

    for (int l = 0; l < lScope.getLightSheetMicroscope().getNumberOfLightSheets(); l++)
    {
      lLaser.setTargetPowerInPercent(1);
      lLaser.setLaserOn(true);
      lLaser.setLaserPowerOn(true);
      lLaser.setLaserOn(true);
      lLaser.setLaserPowerOn(true);

      // Take an image
      SingleViewPlaneImager imager = lScope.getSingleViewPlaneImager();
      imager.setImageWidth(1024);
      imager.setImageHeight(2048);
      imager.setMinZ(25);
      imager.setLightSheetIndex(l);

      // start acquisition
      StackInterface lStack = imager.acquire();

      clij.show(lStack, "stack L" + l);
    }
  }
}
