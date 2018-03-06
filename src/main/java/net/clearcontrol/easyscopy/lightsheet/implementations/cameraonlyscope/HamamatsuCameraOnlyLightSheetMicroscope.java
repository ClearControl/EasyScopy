package net.clearcontrol.easyscopy.lightsheet.implementations.cameraonlyscope;

import clearcl.ClearCLContext;
import clearcontrol.devices.cameras.StackCameraDeviceInterface;
import clearcontrol.devices.cameras.devices.hamamatsu.HamStackCamera;
import clearcontrol.microscope.lightsheet.LightSheetMicroscope;

/**
 * Author: Robert Haase (http://haesleinhuepf.net) at MPI CBG (http://mpi-cbg.de)
 * March 2018
 */
public class HamamatsuCameraOnlyLightSheetMicroscope extends
                                                     LightSheetMicroscope
{
  /**
   * Instantiates a lightsheet microscope with a given name.
   *
   * @param pStackFusionContext            ClearCL context for stack fusion
   * @param pMaxStackProcessingQueueLength max stack processing queue length
   * @param pThreadPoolSize
   */
  public HamamatsuCameraOnlyLightSheetMicroscope(ClearCLContext pStackFusionContext,
                                                 int pMaxStackProcessingQueueLength,
                                                 int pThreadPoolSize)
  {
    super("Hamamatsu Orca Flash Camera only microscope",
          pStackFusionContext,
          pMaxStackProcessingQueueLength,
          pThreadPoolSize);

    long lDefaultStackWidth = 512;
    long lDefaultStackHeight = 512;

    StackCameraDeviceInterface<?> lCamera =
        HamStackCamera.buildWithExternalTriggering(0);

    lCamera.getStackWidthVariable().set(lDefaultStackWidth);
    lCamera.getStackHeightVariable().set(lDefaultStackHeight);
    lCamera.getExposureInSecondsVariable().set(0.010);

    // lCamera.getStackVariable().addSetListener((o,n)->
    // {System.out.println("camera output:"+n);} );

    addDevice(0, lCamera);
  }
}