package org.glob3.mobile.generated; 
//
//  LayerBuilder.cpp
//  G3MiOSSDK
//
//  Created by Mari Luz Mateo on 21/12/12.
//
//

//
//  LayerBuilder.hpp
//  G3MiOSSDK
//
//  Created by Mari Luz Mateo on 21/12/12.
//
//




public class LayerBuilder
{
  public static WMSLayer createBingLayer(boolean enabled)
  {
	WMSLayer bing = new WMSLayer("Bing Virtual Earth", "ve", new URL("http://worldwind27.arc.nasa.gov/wms/virtualearth?", false), WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/jpeg", "EPSG:4326", "", false, null);
	bing.setEnable(enabled);
  
	return bing;
  }
  public static WMSLayer createOSMLayer(boolean enabled)
  {
	WMSLayer osm = new WMSLayer("Open Street Map", "osm_auto:all", new URL("http://129.206.228.72/cached/osm", false), WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/jpeg", "EPSG:4326", "", false, null);
								 // Sector::fromDegrees(-85.05, -180.0, 85.05, 180.0),
	osm.setEnable(enabled);
  
	return osm;
  }
  public static WMSLayer createPNOALayer(boolean enabled)
  {
	WMSLayer pnoa = new WMSLayer("PNOA", "PNOA", new URL("http://www.idee.es/wms/PNOA/PNOA", false), WMSServerVersion.WMS_1_1_0, Sector.fromDegrees(21, -18, 45, 6), "image/png", "EPSG:4326", "", true, null);
	pnoa.setEnable(enabled);
  
	return pnoa;
  }
  public static WMSLayer createBlueMarbleLayer(boolean enabled)
  {
	WMSLayer blueMarble = new WMSLayer("Blue Marble", "bmng200405", new URL("http://www.nasa.network.com/wms?", false), WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/jpeg", "EPSG:4326", "", false, new LevelTileCondition(0, 6));
	blueMarble.setEnable(enabled);
  
	return blueMarble;
  }
  public static WMSLayer createI3LandSatLayer(boolean enabled)
  {
	WMSLayer i3Landsat = new WMSLayer("i3Landsat", "esat", new URL("http://data.worldwind.arc.nasa.gov/wms?", false), WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/jpeg", "EPSG:4326", "", false, new LevelTileCondition(7, 100));
	i3Landsat.setEnable(enabled);
  
	return i3Landsat;
  }
  public static WMSLayer createPoliticalLayer(boolean enabled)
  {
	WMSLayer political = new WMSLayer("Political Boundaries", "topp:cia", new URL("http://worldwind22.arc.nasa.gov/geoserver/wms?", false), WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/png", "EPSG:4326", "countryboundaries", true, null);
	political.setEnable(enabled);
  
	return political;
  }
  public static WMSLayer createCaceresStreetMapLayer(boolean enabled)
  {
	WMSLayer ccStreetMap = new WMSLayer("Callejero Cáceres", URL.escape("Ejes de via"), new URL("http://sig.caceres.es/wms_callejero.mapdef?", false), WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/png", "EPSG:4326", "", true, null);
	ccStreetMap.setEnable(enabled);
  
	return ccStreetMap;
  }
  public static WMSLayer createCanaryIslandStreetMapLayer(boolean enabled)
  {
	WMSLayer canaryStreetMap = new WMSLayer("Callejero Islas Canarias", "VIAS", new URL("http://idecan2.grafcan.es/ServicioWMS/Callejero", false), WMSServerVersion.WMS_1_1_0, Sector.fromDegrees(22.5, -22.5, 33.75, -11.25), "image/gif", "EPSG:4326", "", true, null);
	canaryStreetMap.setEnable(enabled);
  
	return canaryStreetMap;
  }
}