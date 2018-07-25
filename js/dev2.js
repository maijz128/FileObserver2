// ==UserScript==
// @name         DevScript2
// @namespace    https://github.com/maijz128
// @version      0.1.0
// @description  开发用脚本
// @author       MaiJZ
// @match        *://localhost/*
// @require      https://greasyfork.org/scripts/370589/code/FileObserver2.js?version=615301
// @grant        GM_setValue
// @grant        GM_getValue
// @grant        GM_setClipboard
// @grant        unsafeWindow
// @grant        window.close
// @grant        window.focus
// ==/UserScript==

/**
 * 说明：  FileObserver2.js 功能： 自动刷新脚本，配合本地FileObserver.jar使用
 *        用@require 引用本地正在开发的脚本
 */


(function () {
    if(typeof(FileObserver2) === 'undefined'){
        appendScriptLink('https://greasyfork.org/scripts/370589/code/FileObserver2.js?version=615301');
    }
    setTimeout(function () {
        var server = '';
        var checkInterval = 60000;
        window.__FileObserver2 = new FileObserver2(server, checkInterval);
    }, 1000);

})();

function appendScriptLink(src) {
    var f = document.createElement('script');
    f.src = src;
    document.body.appendChild(f);
}