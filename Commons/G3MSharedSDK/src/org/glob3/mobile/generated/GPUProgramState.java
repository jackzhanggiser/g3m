package org.glob3.mobile.generated; 
//
//  GPUProgramState.cpp
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 05/04/13.
//
//

//
//  GPUProgramState.h
//  G3MiOSSDK
//
//  Created by Jose Miguel SN on 05/04/13.
//
//




public class GPUProgramState
{

  private java.util.HashMap<Integer, GPUUniformValue> _uniformValues = new java.util.HashMap<Integer, GPUUniformValue>();
  private java.util.HashMap<Integer, GPUAttributeValue> _attributesValues = new java.util.HashMap<Integer, GPUAttributeValue>();

  private boolean setGPUUniformValue(int key, GPUUniformValue v)
  {
  
    GPUUniform prevLinkedUniform = null;
    boolean uniformExisted = false;
  
  
    GPUUniformValue pv = _uniformValues.get(key);
    if (pv != null){
      uniformExisted = true;
      prevLinkedUniform = pv.getLinkedUniform();
    }
  
    v.linkToGPUUniform(prevLinkedUniform);
    _uniformValues.put(key, v);
  
    if (!uniformExisted)
    {
      onStructureChanged();
    }
  
    return uniformExisted;
  }
  private boolean setGPUAttributeValue(int key, GPUAttributeValue v)
  {
    GPUAttribute prevLinkedAttribute = null;
    boolean attributeExisted = false;
    GPUAttributeValue pv = _attributesValues.get(key);
    if (pv != null){
      attributeExisted = true;
      prevLinkedAttribute = pv.getLinkedAttribute();
    }
  
    v.linkToGPUAttribute(prevLinkedAttribute);
    _attributesValues.put(key, v);
  
    if (!attributeExisted)
    {
      onStructureChanged();
    }
  
    return attributeExisted;
  }

  private java.util.ArrayList<Integer> _uniformKeys;
  private java.util.ArrayList<Integer> _attributeKeys;

  private GPUProgram _lastProgramUsed;

  private void onStructureChanged()
  {
    _uniformKeys = null;
    _uniformKeys = null;
    _lastProgramUsed = null;

    if (_attributeKeys != null)
    {
      _attributeKeys = null;
      _attributeKeys = null;
    }
  }


  public GPUProgramState()
  {
     _lastProgramUsed = null;
     _uniformKeys = null;
     _attributeKeys = null;
  }

  public void dispose()
  {
    clear();
    _uniformKeys = null;
  }

  public final void clear()
  {
    _lastProgramUsed = null;
    _uniformValues.clear();
  
    _attributesValues.clear();
  }

  public final boolean setUniformValue(int key, boolean b)
  {
    return setGPUUniformValue(key, new GPUUniformValueBool(b));
  }

  public final boolean setUniformValue(int key, float f)
  {
    return setGPUUniformValue(key, new GPUUniformValueFloat(f));
  }

  public final boolean setUniformValue(int key, Vector2D v)
  {
    return setGPUUniformValue(key, new GPUUniformValueVec2Float(v._x, v._y));
  }

  public final boolean setUniformValue(int key, double x, double y)
  {
    return setGPUUniformValue(key, new GPUUniformValueVec2Float(x, y));
  }

  public final boolean setUniformValue(int key, double x, double y, double z, double w)
  {
    return setGPUUniformValue(key, new GPUUniformValueVec4Float(x,y,z,w));
  }

  public final boolean setUniformMatrixValue(int key, MutableMatrix44D m, boolean isTransform)
  {
    GPUUniformValueMatrix4FloatTransform uv = new GPUUniformValueMatrix4FloatTransform(m, isTransform);
    return setGPUUniformValue(key, uv);
  }

  public final boolean setAttributeValue(int key, IFloatBuffer buffer, int attributeSize, int arrayElementSize, int index, boolean normalized, int stride)
  {
    switch (attributeSize)
    {
      case 1:
        return setGPUAttributeValue(key, new GPUAttributeValueVec1Float(buffer, arrayElementSize, index, stride, normalized));
      case 2:
        return setGPUAttributeValue(key, new GPUAttributeValueVec2Float(buffer, arrayElementSize, index, stride, normalized));
      case 3:
        return setGPUAttributeValue(key, new GPUAttributeValueVec3Float(buffer, arrayElementSize, index, stride, normalized));
      case 4:
        return setGPUAttributeValue(key, new GPUAttributeValueVec4Float(buffer, arrayElementSize, index, stride, normalized));
      default:
        ILogger.instance().logError("Invalid size for Attribute.");
        return false;
    }
  }

  public final void setAttributeEnabled(int key, boolean enabled)
  {
    //TODO: REMOVE FUNCTION
    if (!enabled)
    {
      setAttributeDisabled(key);
    }
  }
  public final void setAttributeDisabled(int key)
  {
    setGPUAttributeValue(key, new GPUAttributeValueDisabled());
  }

  public final void applyChanges(GL gl)
  {
    if (_lastProgramUsed == null)
    {
      ILogger.instance().logError("Trying to use unlinked GPUProgramState.");
    }
    applyValuesToLinkedProgram();
    _lastProgramUsed.applyChanges(gl);
  }

  public final void linkToProgram(GPUProgram prog)
  {
  
    if (_lastProgramUsed == prog)
    {
      return; //Already linked
    }
  
  
    for (java.util.Map.Entry<Integer, GPUUniformValue> entry : _uniformValues.entrySet()){
      final Integer key = entry.getKey();
      final GPUUniformValue v = entry.getValue();
  
      if (!v.linkToGPUProgram(prog, key)){
        return;
      }
    }
  
    for (java.util.Map.Entry<Integer, GPUAttributeValue> entry : _attributesValues.entrySet()){
      final Integer key = entry.getKey();
      final GPUAttributeValue v = entry.getValue();
  
      if (!v.linkToGPUProgram(prog, key)){
        return;
      }
    }
  
  
  
    _lastProgramUsed = prog;
  }

  public final boolean isLinkedToProgram()
  {
    return _lastProgramUsed != null;
  }

  public final GPUProgram getLinkedProgram()
  {
    return _lastProgramUsed;
  }

  public final java.util.ArrayList<Integer> getUniformsKeys()
  {
  
    if (_uniformKeys == null)
    {
  
      _uniformKeys = new java.util.ArrayList<Integer>();
  
  
      _uniformKeys.addAll(_uniformValues.keySet());
  
    }
    return _uniformKeys;
  }
  public final java.util.ArrayList<Integer> getAttributeKeys()
  {
  
    if (_attributeKeys == null)
    {
  
      _attributeKeys = new java.util.ArrayList<Integer>();
  
      _attributeKeys.addAll(_attributesValues.keySet());
  
  
    }
    return _attributeKeys;
  }

  public final String description()
  {
    IStringBuilder isb = IStringBuilder.newStringBuilder();
    isb.addString("PROGRAM STATE\n==========\n");
    //TODO: IMPLEMENT
    String s = isb.getString();
    if (isb != null)
       isb.dispose();
    return s;
  }

  public final void applyValuesToLinkedProgram()
  {
    for (final GPUUniformValue u : _uniformValues.values()){
      u.setValueToLinkedUniform();
    }
  
    for (final GPUAttributeValue a : _attributesValues.values()){
      a.setValueToLinkedAttribute();
    }
  }


  /*
   bool setUniformValue(const std::string& name, bool b){
   return setUniformValue(GPUVariable::getKeyForName(name, UNIFORM), b);
   }
   
   bool setUniformValue(const std::string& name, float f){
   return setUniformValue(GPUVariable::getKeyForName(name, UNIFORM), f);
   }
   
   bool setUniformValue(const std::string& name, const Vector2D& v){
   return setUniformValue(GPUVariable::getKeyForName(name, UNIFORM), v);
   }
   
   bool setUniformValue(const std::string& name, double x, double y){
   return setUniformValue(GPUVariable::getKeyForName(name, UNIFORM), x, y);
   }
   
   bool setUniformValue(const std::string& name, double x, double y, double z, double w){
   return setUniformValue(GPUVariable::getKeyForName(name, UNIFORM), x, y, z, w);
   }
   
   bool setUniformMatrixValue(const std::string& name, const MutableMatrix44D& m, bool isTransform){
   return setUniformMatrixValue(GPUVariable::getKeyForName(name, UNIFORM), m, isTransform);
   }
   
   bool setAttributeValue(const std::string& name,
   IFloatBuffer* buffer, int attributeSize,
   int arrayElementSize, int index, bool normalized, int stride){
   return setAttributeValue(GPUVariable::getKeyForName(name, ATTRIBUTE),
   buffer, attributeSize,
   arrayElementSize, index, normalized, stride);
   }
   
   void setAttributeEnabled(const std::string& name, bool enabled){
   setAttributeEnabled(GPUVariable::getKeyForName(name, ATTRIBUTE), enabled);
   }
   void setAttributeDisabled(const std::string& name){
   setAttributeDisabled(GPUVariable::getKeyForName(name, ATTRIBUTE));
   }
   */

}