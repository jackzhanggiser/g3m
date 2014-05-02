package org.glob3.mobile.generated; 
//
//  TiledVectorLayerTileImageProvider.cpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 4/30/14.
//
//

//
//  TiledVectorLayerTileImageProvider.hpp
//  G3MiOSSDK
//
//  Created by Diego Gomez Deck on 4/30/14.
//
//



//class TiledVectorLayer;
//class IDownloader;
//class GEOObject;

public class TiledVectorLayerTileImageProvider extends TileImageProvider
{

  private static class GEOJSONBufferParser extends GAsyncTask
  {
    private IByteBuffer _buffer;
    private GEOObject _geoObject;

    public GEOJSONBufferParser(IByteBuffer buffer)
    {
       _buffer = buffer;
       _geoObject = null;
    }

    public void dispose()
    {
      if (_buffer != null)
         _buffer.dispose();
      if (_geoObject != null)
         _geoObject.dispose();
      super.dispose();
    }

    public final void runInBackground(G3MContext context)
    {
      _geoObject = GEOJSONParser.parseJSON(_buffer);
      if (_buffer != null)
         _buffer.dispose();
      _buffer = null;
    }

    public final void onPostExecute(G3MContext context)
    {
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#warning Diego at work!
      ILogger.instance().logInfo("Parsed geojson");
    }

  }


  private static class GEOJSONBufferDownloadListener extends IBufferDownloadListener
  {
    private TiledVectorLayerTileImageProvider _tiledVectorLayerTileImageProvider;
    private final String _tileId;
    private TileImageListener _listener;
    private final boolean _deleteListener;
    private final IThreadUtils _threadUtils;
    private TileImageContribution _contribution;

    public GEOJSONBufferDownloadListener(TiledVectorLayerTileImageProvider tiledVectorLayerTileImageProvider, String tileId, TileImageContribution contribution, TileImageListener listener, boolean deleteListener, IThreadUtils threadUtils)
    {
       _tiledVectorLayerTileImageProvider = tiledVectorLayerTileImageProvider;
       _tileId = tileId;
       _contribution = contribution;
       _listener = listener;
       _deleteListener = deleteListener;
       _threadUtils = threadUtils;
    }

    public void dispose()
    {
      _tiledVectorLayerTileImageProvider.requestFinish(_tileId);
    
      if (_deleteListener)
      {
        if (_listener != null)
           _listener.dispose();
      }
    
      TileImageContribution.deleteContribution(_contribution);
    
      super.dispose();
    }

    public final void onDownload(URL url, IByteBuffer buffer, boolean expired)
    {
    //  _listener->imageCreated(_tileId,
    //                          image,
    //                          url.getPath(),
    //                          _contribution);
    //  _contribution = NULL;
    
      _threadUtils.invokeAsyncTask(new GEOJSONBufferParser(buffer), true);
    
    
//C++ TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#warning Diego at work!
      _listener.imageCreationError(_tileId, "NOT YET IMPLEMENTED");
    
    //  delete buffer;
      TileImageContribution.deleteContribution(_contribution);
      _contribution = null;
    }

    public final void onError(URL url)
    {
      _listener.imageCreationError(_tileId, "Download error - " + url.getPath());
    }

    public final void onCancel(URL url)
    {
      _listener.imageCreationCanceled(_tileId);
    }

    public final void onCanceledDownload(URL url, IByteBuffer buffer, boolean expired)
    {
      // do nothing
    }

  }


//  class ImageAssembler {
//  private:
//    TiledVectorLayerTileImageProvider* _tiledVectorLayerTileImageProvider;
//    const std::string                  _tileId;
//    TileImageListener*                 _listener;
//    const bool                         _deleteListener;
//    const IThreadUtils*                _threadUtils;
///#ifdef C_CODE
//    const TileImageContribution*       _contribution;
///#endif
///#ifdef JAVA_CODE
//    private TileImageContribution _contribution;
///#endif
//
//
//    GEOJSONBufferDownloadListener* _downloadListener;
//    GEOJSONBufferParser*           _parser;
//  public:
//    ImageAssembler(TiledVectorLayerTileImageProvider* tiledVectorLayerTileImageProvider,
//                                  const std::string&                 tileId,
//                                  const TileImageContribution*       contribution,
//                                  TileImageListener*                 listener,
//                                  bool                               deleteListener,
//                                  const IThreadUtils*                threadUtils) :
//    _tiledVectorLayerTileImageProvider(tiledVectorLayerTileImageProvider),
//    _tileId(tileId),
//    _contribution(contribution),
//    _listener(listener),
//    _deleteListener(deleteListener),
//    _threadUtils(threadUtils)
//    {
//      _downloadListener =
//    }
//
//  };


  private final TiledVectorLayer _layer;
  private IDownloader _downloader;
  private final IThreadUtils _threadUtils;

  private final java.util.HashMap<String, Long> _requestsIdsPerTile = new java.util.HashMap<String, Long>();


  public TiledVectorLayerTileImageProvider(TiledVectorLayer layer, IDownloader downloader, IThreadUtils threadUtils)
  {
     _layer = layer;
     _downloader = downloader;
     _threadUtils = threadUtils;
  }


  public final TileImageContribution contribution(Tile tile)
  {
    return _layer.contribution(tile);
  }

  public final void create(Tile tile, TileImageContribution contribution, Vector2I resolution, long tileDownloadPriority, boolean logDownloadActivity, TileImageListener listener, boolean deleteListener, FrameTasksExecutor frameTasksExecutor)
  {
    final String tileId = tile._id;
  
  //  ImageAssembler* assembler = new ImageAssembler(this,
  //                                                 tileId,
  //                                                 contribution,
  //                                                 listener,
  //                                                 deleteListener,
  //                                                 _threadUtils);
  //  aa;
  
    final long requestId = _layer.requestGEOJSONBuffer(tile, _downloader, tileDownloadPriority, logDownloadActivity, new GEOJSONBufferDownloadListener(this, tileId, contribution, listener, deleteListener, _threadUtils), true); // deleteListener
  
    if (requestId >= 0)
    {
      _requestsIdsPerTile.put(tileId, requestId);
    }
  }

  public final void cancel(String tileId)
  {
    final Long requestId = _requestsIdsPerTile.remove(tileId);
    if (requestId != null) {
      _downloader.cancelRequest(requestId);
    }
  }

  public final void requestFinish(String tileId)
  {
    _requestsIdsPerTile.remove(tileId);
  }

}