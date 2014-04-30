package org.glob3.mobile.generated; 
public class WMSLayer extends RasterLayer
{

  private final URL _mapServerURL;
  private final URL _queryServerURL;

  private final String _mapLayer;
  private final WMSServerVersion _mapServerVersion;
  private final String _queryLayer;
  private final WMSServerVersion _queryServerVersion;
  private final Sector _sector ;
  private final String _format;
  private final String _srs;
  private final String _style;
  private final boolean _isTransparent;
  private String _extraParameter;

  private double toBBOXLongitude(Angle longitude)
  {
    return (_parameters._mercator) ? MercatorUtils.longitudeToMeters(longitude) : longitude._degrees;
  }
  private double toBBOXLatitude(Angle latitude)
  {
    return (_parameters._mercator) ? MercatorUtils.latitudeToMeters(latitude) : latitude._degrees;
  }

  protected final String getLayerType()
  {
    return "WMS";
  }

  protected final boolean rawIsEquals(Layer that)
  {
    WMSLayer t = (WMSLayer) that;
  
    if (!(_mapServerURL.isEquals(t._mapServerURL)))
    {
      return false;
    }
  
    if (!(_queryServerURL.isEquals(t._queryServerURL)))
    {
      return false;
    }
  
    if (!_mapLayer.equals(t._mapLayer))
    {
      return false;
    }
  
    if (_mapServerVersion != t._mapServerVersion)
    {
      return false;
    }
  
    if (!_queryLayer.equals(t._queryLayer))
    {
      return false;
    }
  
    if (_queryServerVersion != t._queryServerVersion)
    {
      return false;
    }
  
    if (!(_sector.isEquals(t._sector)))
    {
      return false;
    }
  
    if (!_format.equals(t._format))
    {
      return false;
    }
  
    if (_queryServerVersion != t._queryServerVersion)
    {
      return false;
    }
  
    if (!_srs.equals(t._srs))
    {
      return false;
    }
  
    if (!_style.equals(t._style))
    {
      return false;
    }
  
    if (_isTransparent != t._isTransparent)
    {
      return false;
    }
  
    if (!_extraParameter.equals(t._extraParameter))
    {
      return false;
    }
  
    return true;
  }


  protected final TileImageContribution rawContribution(Tile tile)
  {
    final Sector tileSector = tile._sector;
  
    if (!_sector.touchesWith(tileSector))
    {
      return null;
    }
    else if (_sector.fullContains(tileSector))
    {
      return ((_isTransparent || (_transparency < 1)) ? TileImageContribution.fullCoverageTransparent(_transparency) : TileImageContribution.fullCoverageOpaque());
    }
    else
    {
      final Sector contributionSector = _sector.intersection(tileSector);
      return ((_isTransparent || (_transparency < 1)) ? TileImageContribution.partialCoverageTransparent(contributionSector, _transparency) : TileImageContribution.partialCoverageOpaque(contributionSector));
    }
  }

  protected final URL createURL(Tile tile)
  {
  
    final String path = _mapServerURL.getPath();
  //  if (path.empty()) {
  //    return petitions;
  //  }
  
    final Sector tileSector = tile._sector;
  //  if (!_sector.touchesWith(tileSector)) {
  //    return petitions;
  //  }
  //
    final Sector sector = tileSector.intersection(_sector);
  //  if (sector._deltaLatitude.isZero() ||
  //      sector._deltaLongitude.isZero() ) {
  //    return petitions;
  //  }
  
    //TODO: MUST SCALE WIDTH,HEIGHT
  
    final Vector2I tileTextureResolution = _parameters._tileTextureResolution;
  
     //Server name
    String req = path;
     if (req.charAt(req.length() - 1) != '?')
     {
        req += '?';
     }
  
    //  //If the server refer to itself as localhost...
    //  const int localhostPos = req.find("localhost");
    //  if (localhostPos != -1) {
    //    req = req.substr(localhostPos+9);
    //
    //    const int slashPos = req.find("/", 8);
    //    std::string newHost = req.substr(0, slashPos);
    //
    //    req = newHost + req;
    //  }
  
    req += "REQUEST=GetMap&SERVICE=WMS";
  
  
    switch (_mapServerVersion)
    {
      case WMS_1_3_0:
      {
        req += "&VERSION=1.3.0";
  
        IStringBuilder isb = IStringBuilder.newStringBuilder();
  
        isb.addString("&WIDTH=");
        isb.addInt(tileTextureResolution._x);
        isb.addString("&HEIGHT=");
        isb.addInt(tileTextureResolution._y);
  
        isb.addString("&BBOX=");
        isb.addDouble(toBBOXLatitude(sector._lower._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(sector._lower._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(sector._upper._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(sector._upper._longitude));
  
        req += isb.getString();
        if (isb != null)
           isb.dispose();
  
        req += "&CRS=EPSG:4326";
  
        break;
      }
      case WMS_1_1_0:
      default:
      {
        // default is 1.1.1
        req += "&VERSION=1.1.1";
  
        IStringBuilder isb = IStringBuilder.newStringBuilder();
  
        isb.addString("&WIDTH=");
        isb.addInt(tileTextureResolution._x);
        isb.addString("&HEIGHT=");
        isb.addInt(tileTextureResolution._y);
  
        isb.addString("&BBOX=");
        isb.addDouble(toBBOXLongitude(sector._lower._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(sector._lower._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(sector._upper._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(sector._upper._latitude));
  
        req += isb.getString();
        if (isb != null)
           isb.dispose();
        break;
      }
    }
  
    req += "&LAYERS=" + _mapLayer;
  
     req += "&FORMAT=" + _format;
  
    if (!_srs.equals(""))
    {
      req += "&SRS=" + _srs;
    }
     else
     {
      req += "&SRS=EPSG:4326";
    }
  
    //Style
    if (!_style.equals(""))
    {
      req += "&STYLES=" + _style;
    }
     else
     {
      req += "&STYLES=";
    }
  
    //ASKING TRANSPARENCY
    if (_isTransparent)
    {
      req += "&TRANSPARENT=TRUE";
    }
    else
    {
      req += "&TRANSPARENT=FALSE";
    }
  
    if (_extraParameter.compareTo("") != 0)
    {
      req += "&";
      req += _extraParameter;
    }
  
    return new URL(req, false);
  }


  public WMSLayer(String mapLayer, URL mapServerURL, WMSServerVersion mapServerVersion, String queryLayer, URL queryServerURL, WMSServerVersion queryServerVersion, Sector sector, String format, String srs, String style, boolean isTransparent, LayerCondition condition, TimeInterval timeToCache, boolean readExpired, LayerTilesRenderParameters parameters)
  {
     this(mapLayer, mapServerURL, mapServerVersion, queryLayer, queryServerURL, queryServerVersion, sector, format, srs, style, isTransparent, condition, timeToCache, readExpired, parameters, 1);
  }
  public WMSLayer(String mapLayer, URL mapServerURL, WMSServerVersion mapServerVersion, String queryLayer, URL queryServerURL, WMSServerVersion queryServerVersion, Sector sector, String format, String srs, String style, boolean isTransparent, LayerCondition condition, TimeInterval timeToCache, boolean readExpired)
  {
     this(mapLayer, mapServerURL, mapServerVersion, queryLayer, queryServerURL, queryServerVersion, sector, format, srs, style, isTransparent, condition, timeToCache, readExpired, null, 1);
  }
  public WMSLayer(String mapLayer, URL mapServerURL, WMSServerVersion mapServerVersion, String queryLayer, URL queryServerURL, WMSServerVersion queryServerVersion, Sector sector, String format, String srs, String style, boolean isTransparent, LayerCondition condition, TimeInterval timeToCache, boolean readExpired, LayerTilesRenderParameters parameters, float transparency)
  {
     super(timeToCache, readExpired, (parameters == null) ? LayerTilesRenderParameters.createDefaultWGS84(Sector.fullSphere()) : parameters, transparency, condition);
     _mapLayer = mapLayer;
     _mapServerURL = mapServerURL;
     _mapServerVersion = mapServerVersion;
     _queryLayer = queryLayer;
     _queryServerURL = queryServerURL;
     _queryServerVersion = queryServerVersion;
     _sector = new Sector(sector);
     _format = format;
     _srs = srs;
     _style = style;
     _isTransparent = isTransparent;
     _extraParameter = "";
  
  }

  public WMSLayer(String mapLayer, URL mapServerURL, WMSServerVersion mapServerVersion, Sector sector, String format, String srs, String style, boolean isTransparent, LayerCondition condition, TimeInterval timeToCache, boolean readExpired, LayerTilesRenderParameters parameters)
  {
     this(mapLayer, mapServerURL, mapServerVersion, sector, format, srs, style, isTransparent, condition, timeToCache, readExpired, parameters, 1);
  }
  public WMSLayer(String mapLayer, URL mapServerURL, WMSServerVersion mapServerVersion, Sector sector, String format, String srs, String style, boolean isTransparent, LayerCondition condition, TimeInterval timeToCache, boolean readExpired)
  {
     this(mapLayer, mapServerURL, mapServerVersion, sector, format, srs, style, isTransparent, condition, timeToCache, readExpired, null, 1);
  }
  public WMSLayer(String mapLayer, URL mapServerURL, WMSServerVersion mapServerVersion, Sector sector, String format, String srs, String style, boolean isTransparent, LayerCondition condition, TimeInterval timeToCache, boolean readExpired, LayerTilesRenderParameters parameters, float transparency)
  {
     super(timeToCache, readExpired, (parameters == null) ? LayerTilesRenderParameters.createDefaultWGS84(Sector.fullSphere()) : parameters, transparency, condition);
     _mapLayer = mapLayer;
     _mapServerURL = mapServerURL;
     _mapServerVersion = mapServerVersion;
     _queryLayer = mapLayer;
     _queryServerURL = mapServerURL;
     _queryServerVersion = mapServerVersion;
     _sector = new Sector(sector);
     _format = format;
     _srs = srs;
     _style = style;
     _isTransparent = isTransparent;
     _extraParameter = "";
  
  }


  public final java.util.ArrayList<Petition> createTileMapPetitions(G3MRenderContext rc, LayerTilesRenderParameters layerTilesRenderParameters, Tile tile)
  {
    java.util.ArrayList<Petition> petitions = new java.util.ArrayList<Petition>();
  
    final String path = _mapServerURL.getPath();
    if (path.length() == 0)
    {
      return petitions;
    }
  
    final Sector tileSector = tile._sector;
    if (!_sector.touchesWith(tileSector))
    {
      return petitions;
    }
  
    final Sector sector = tileSector.intersection(_sector);
    if (sector._deltaLatitude.isZero() || sector._deltaLongitude.isZero())
    {
      return petitions;
    }
  
    //TODO: MUST SCALE WIDTH,HEIGHT
  
    final Vector2I tileTextureResolution = _parameters._tileTextureResolution;
  
     //Server name
    String req = path;
     if (req.charAt(req.length() - 1) != '?')
     {
        req += '?';
     }
  
    //  //If the server refer to itself as localhost...
    //  const int localhostPos = req.find("localhost");
    //  if (localhostPos != -1) {
    //    req = req.substr(localhostPos+9);
    //
    //    const int slashPos = req.find("/", 8);
    //    std::string newHost = req.substr(0, slashPos);
    //
    //    req = newHost + req;
    //  }
  
    req += "REQUEST=GetMap&SERVICE=WMS";
  
  
    switch (_mapServerVersion)
    {
      case WMS_1_3_0:
      {
        req += "&VERSION=1.3.0";
  
        IStringBuilder isb = IStringBuilder.newStringBuilder();
  
        isb.addString("&WIDTH=");
        isb.addInt(tileTextureResolution._x);
        isb.addString("&HEIGHT=");
        isb.addInt(tileTextureResolution._y);
  
        isb.addString("&BBOX=");
        isb.addDouble(toBBOXLatitude(sector._lower._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(sector._lower._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(sector._upper._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(sector._upper._longitude));
  
        req += isb.getString();
        if (isb != null)
           isb.dispose();
  
        req += "&CRS=EPSG:4326";
  
        break;
      }
      case WMS_1_1_0:
      default:
      {
        // default is 1.1.1
        req += "&VERSION=1.1.1";
  
        IStringBuilder isb = IStringBuilder.newStringBuilder();
  
        isb.addString("&WIDTH=");
        isb.addInt(tileTextureResolution._x);
        isb.addString("&HEIGHT=");
        isb.addInt(tileTextureResolution._y);
  
        isb.addString("&BBOX=");
        isb.addDouble(toBBOXLongitude(sector._lower._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(sector._lower._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(sector._upper._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(sector._upper._latitude));
  
        req += isb.getString();
        if (isb != null)
           isb.dispose();
        break;
      }
    }
  
    req += "&LAYERS=" + _mapLayer;
  
     req += "&FORMAT=" + _format;
  
    if (!_srs.equals(""))
    {
      req += "&SRS=" + _srs;
    }
     else
     {
      req += "&SRS=EPSG:4326";
    }
  
    //Style
    if (!_style.equals(""))
    {
      req += "&STYLES=" + _style;
    }
     else
     {
      req += "&STYLES=";
    }
  
    //ASKING TRANSPARENCY
    if (_isTransparent)
    {
      req += "&TRANSPARENT=TRUE";
    }
    else
    {
      req += "&TRANSPARENT=FALSE";
    }
  
    if (_extraParameter.compareTo("") != 0)
    {
      req += "&";
      req += _extraParameter;
    }
  
    //  printf("Request: %s\n", req.c_str());
  
    Petition petition = new Petition(sector, new URL(req, false), getTimeToCache(), getReadExpired(), _isTransparent, _transparency);
    petitions.add(petition);
  
     return petitions;
  }

  public final URL getFeatureInfoURL(Geodetic2D position, Sector tileSector)
  {
    if (!_sector.touchesWith(tileSector))
    {
      return URL.nullURL();
    }
  
    final Sector intersectionSector = tileSector.intersection(_sector);
  
     //Server name
    String req = _queryServerURL.getPath();
     if (req.charAt(req.length()-1) != '?')
     {
        req += '?';
     }
  
    //If the server refer to itself as localhost...
    int pos = req.indexOf("localhost");
    if (pos != -1)
    {
      req = req.substring(pos+9);
  
      int pos2 = req.indexOf("/", 8);
      String newHost = req.substring(0, pos2);
  
      req = newHost + req;
    }
  
    req += "REQUEST=GetFeatureInfo&SERVICE=WMS";
  
    //SRS
    if (!_srs.equals(""))
    {
      req += "&SRS=" + _srs;
    }
     else
     {
      req += "&SRS=EPSG:4326";
    }
  
    switch (_queryServerVersion)
    {
      case WMS_1_3_0:
      {
        req += "&VERSION=1.3.0";
  
        IStringBuilder isb = IStringBuilder.newStringBuilder();
  
        isb.addString("&WIDTH=");
        isb.addInt(_parameters._tileTextureResolution._x);
        isb.addString("&HEIGHT=");
        isb.addInt(_parameters._tileTextureResolution._y);
  
        isb.addString("&BBOX=");
        isb.addDouble(toBBOXLatitude(intersectionSector._lower._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(intersectionSector._lower._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(intersectionSector._upper._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(intersectionSector._upper._longitude));
  
        req += isb.getString();
  
        if (isb != null)
           isb.dispose();
  
        req += "&CRS=EPSG:4326";
  
        break;
      }
      case WMS_1_1_0:
      default:
      {
        // default is 1.1.1
        req += "&VERSION=1.1.1";
  
        IStringBuilder isb = IStringBuilder.newStringBuilder();
  
        isb.addString("&WIDTH=");
        isb.addInt(_parameters._tileTextureResolution._x);
        isb.addString("&HEIGHT=");
        isb.addInt(_parameters._tileTextureResolution._y);
  
        isb.addString("&BBOX=");
        isb.addDouble(toBBOXLongitude(intersectionSector._lower._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(intersectionSector._lower._latitude));
        isb.addString(",");
        isb.addDouble(toBBOXLongitude(intersectionSector._upper._longitude));
        isb.addString(",");
        isb.addDouble(toBBOXLatitude(intersectionSector._upper._latitude));
  
        req += isb.getString();
  
        if (isb != null)
           isb.dispose();
        break;
      }
    }
    req += "&LAYERS=" + _queryLayer;
    req += "&QUERY_LAYERS=" + _queryLayer;
  
    req += "&INFO_FORMAT=text/plain";
  
    final IMathUtils mu = IMathUtils.instance();
  
    double u;
    double v;
    if (_parameters._mercator)
    {
      u = intersectionSector.getUCoordinate(position._longitude);
      v = MercatorUtils.getMercatorV(position._latitude);
    }
    else
    {
      final Vector2D uv = intersectionSector.getUVCoordinates(position);
      u = uv._x;
      v = uv._y;
    }
  
    //X and Y
    //const Vector2D uv = sector.getUVCoordinates(position);
    final long x = mu.round((u * _parameters._tileTextureResolution._x));
    final long y = mu.round((v * _parameters._tileTextureResolution._y));
  
    IStringBuilder isb = IStringBuilder.newStringBuilder();
    isb.addString("&X=");
    isb.addLong(x);
    isb.addString("&Y=");
    isb.addLong(y);
    req += isb.getString();
    if (isb != null)
       isb.dispose();
  
     return new URL(req, false);
  }


  public final void setExtraParameter(String extraParameter)
  {
    _extraParameter = extraParameter;
    notifyChanges();
  }

  public final String description()
  {
    return "[WMSLayer]";
  }

  public final WMSLayer copy()
  {
    return new WMSLayer(_mapLayer, _mapServerURL, _mapServerVersion, _queryLayer, _queryServerURL, _queryServerVersion, _sector, _format, _srs, _style, _isTransparent, (_condition == null) ? null : _condition.copy(), TimeInterval.fromMilliseconds(_timeToCacheMS), _readExpired, (_parameters == null) ? null : _parameters.copy());
  }

  public final RenderState getRenderState()
  {
    _errors.clear();
    if (_mapLayer.compareTo("") == 0)
    {
      _errors.add("Missing layer parameter: mapLayer");
    }
    final String mapServerUrl = _mapServerURL.getPath();
    if (mapServerUrl.compareTo("") == 0)
    {
      _errors.add("Missing layer parameter: mapServerURL");
    }
    if (_format.compareTo("") == 0)
    {
      _errors.add("Missing layer parameter: format");
    }
  
    if (_errors.size() > 0)
    {
      return RenderState.error(_errors);
    }
    return RenderState.ready();
  }
}