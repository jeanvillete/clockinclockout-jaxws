package com.clkio.ws.impl;

import javax.jws.WebService;

import com.clkio.ws.UserWS;

@WebService( endpointInterface = "com.clkio.ws.UserWS" )
public class UserWSImpl extends WebServiceCommon implements UserWS {

}
