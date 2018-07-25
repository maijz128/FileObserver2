// ==UserScript==
// @name         FileObserver2
// @namespace    https://github.com/maijz128
// @version      0.1.0
// @description  开发用。Update:2018-07-25
// @author       MaiJZ
// @grant        none
// ==/UserScript==

(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
        typeof define === 'function' && define.amd ? define(factory) :
        (global.FileObserver2 = factory());
}(this, (function () {
    'use strict';

    function FileObserver2(server, checkInterval) {
        var self = this;
        this._isDebug = false;
        this._server = server || "ws://localhost:8293";
        this._IS_CHANGED = "IS_CHANGED";
        this._checkStateInterval = checkInterval || 30000;
        this._isConnected = false;
        this.onConnected = null; // callback method
        this.socket = null;

        this._createWebSocket = function (url) {
            // Create WebSocket connection.
            self.socket = new WebSocket(url);
            self._initEvent(self.socket); 
        };
        this._initEvent = function (socketClient) {
            socketClient.onopen = function (event) {
                if(self._isDebug) console.log('FileObserver2: is opened.');
            };

            socketClient.onclose = function (event) {
                if(self._isDebug) console.log('FileObserver2: is closed.');
            };

            socketClient.onerror = function (event) {
                if(self._isDebug) console.log('FileObserver2: error!');
            };

            socketClient.onmessage = function (event) {
                var msg = event.data;
                if (msg === self._IS_CHANGED) {
                    self.closeConnect();
                    location.reload();
                }
            };
        };
        this.connect = function () {
            self._createWebSocket(self._server);
        };
        this.closeConnect = function () {
            if (self.isConnected()) self.socket.close();
        };
        this.isConnected = function(){
            return self.socket !== null && self.socket.readyState === WebSocket.OPEN;
        };
        this.isClosed = function () {
            return !self.isConnected();
        };

        this._CheckWorker = setInterval(function () {
            if (self.isClosed()) {
                if (self._isDebug) console.log('FileObserver2: connect again ...');
                self.connect();
            }
        }, this._checkStateInterval);


        this.connect();
    }

    return window.FileObserver2 || FileObserver2;
})));