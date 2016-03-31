//
//  IFactory.hpp
//  G3MiOSSDK
//
//  Created by Agustin Trujillo Pino on 31/05/12.
//

#ifndef G3MiOSSDK_IFactory
#define G3MiOSSDK_IFactory

#include "ILogger.hpp"

class ITimer;
class IImage;
class IFloatBuffer;
class IIntBuffer;
class IShortBuffer;
class IByteBuffer;
class ILogger;
class IImageListener;
class ICanvas;
class IWebSocket;
class IWebSocketListener;
class URL;
class IDeviceInfo;
class IXMLNode;

class IFactory {
private:
  static IFactory*     _instance;

  mutable IDeviceInfo* _deviceInfo;

protected:
  virtual IDeviceInfo* createDeviceInfo() const = 0;

public:
  static void setInstance(IFactory* factory) {
    if (_instance != NULL) {
      ILogger::instance()->logWarning("IFactory instance already set!");
      delete _instance;
    }
    _instance = factory;
  }

  static const IFactory* instance() {
    return _instance;
  }

  IFactory() :
  _deviceInfo(NULL)
  {

  }

  virtual ~IFactory() {
  }

  virtual ITimer* createTimer() const = 0;

  virtual IFloatBuffer* createFloatBuffer(size_t size) const = 0;

  /* special factory method for creating floatbuffers from matrix */
  virtual IFloatBuffer* createFloatBuffer(float f0,
                                          float f1,
                                          float f2,
                                          float f3,
                                          float f4,
                                          float f5,
                                          float f6,
                                          float f7,
                                          float f8,
                                          float f9,
                                          float f10,
                                          float f11,
                                          float f12,
                                          float f13,
                                          float f14,
                                          float f15) const = 0;

  virtual IIntBuffer* createIntBuffer(size_t size) const = 0;

  virtual IShortBuffer* createShortBuffer(size_t size) const = 0;

  virtual IByteBuffer* createByteBuffer(size_t length) const = 0;

  virtual IByteBuffer* createByteBuffer(unsigned char data[], size_t length) const = 0;

  virtual ICanvas* createCanvas() const = 0;

  virtual IWebSocket* createWebSocket(const URL& url,
                                      IWebSocketListener* listener,
                                      bool autodeleteListener,
                                      bool autodeleteWebSocket) const = 0;

  const IDeviceInfo* getDeviceInfo() const;
  
  virtual IXMLNode* createXMLNodeFromXML(const std::string& xmlText) const = 0;


#ifdef JAVA_CODE
  public abstract IShortBuffer createShortBuffer(final short[] array, final int length);
  public abstract IFloatBuffer createFloatBuffer(final float[] array, final int length);
#endif
  
};

#endif
