Sample for wsimporting and generating the proper stubs locally.

  * local resource (clkio-jaxws.wsdl)
    wsimport -keep -Xnocompile -s src -verbose -b xsd-custom-binding.xml -b wsdl-custom-binding.xml clkio-jaxws.wsdl

  * remote resource (ws.clkio.com)
    wsimport -keep -Xnocompile -s src -verbose -b xsd-custom-binding.xml -b wsdl-custom-binding.xml http://ws.clkio.com
