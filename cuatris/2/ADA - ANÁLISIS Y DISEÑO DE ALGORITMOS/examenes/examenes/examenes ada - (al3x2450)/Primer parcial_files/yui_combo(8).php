/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("attribute-complex",function(b){var a=b.Object,c=".";b.Attribute.Complex=function(){};b.Attribute.Complex.prototype={_normAttrVals:function(g){var i={},h={},j,d,f,e;if(g){for(e in g){if(g.hasOwnProperty(e)){if(e.indexOf(c)!==-1){j=e.split(c);d=j.shift();f=h[d]=h[d]||[];f[f.length]={path:j,value:g[e]};}else{i[e]=g[e];}}}return{simple:i,complex:h};}else{return null;}},_getAttrInitVal:function(m,j,p){var e=j.value,o=j.valueFn,d,f,h,g,q,n,k;if(o){if(!o.call){o=this[o];}if(o){e=o.call(this);}}if(!j.readOnly&&p){d=p.simple;if(d&&d.hasOwnProperty(m)){e=d[m];}f=p.complex;if(f&&f.hasOwnProperty(m)){k=f[m];for(h=0,g=k.length;h<g;++h){q=k[h].path;n=k[h].value;a.setValue(e,q,n);}}}return e;}};b.mix(b.Attribute,b.Attribute.Complex,true,null,1);},"3.4.1",{requires:["attribute-base"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("json-parse",function(b){function k(e){return(b.config.win||this||{})[e];}var j=k("JSON"),l=(Object.prototype.toString.call(j)==="[object JSON]"&&j),f=!!l,o=/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,m=/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,d=/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,g=/(?:^|:|,)(?:\s*\[)+/g,p=/[^\],:{}\s]/,n=function(e){return"\\u"+("0000"+(+(e.charCodeAt(0))).toString(16)).slice(-4);},c=function(r,e){var q=function(x,u){var t,s,w=x[u];if(w&&typeof w==="object"){for(t in w){if(w.hasOwnProperty(t)){s=q(w,t);if(s===undefined){delete w[t];}else{w[t]=s;}}}}return e.call(x,u,w);};return typeof e==="function"?q({"":r},""):r;},h=function(q,e){q=q.replace(o,n);if(!p.test(q.replace(m,"@").replace(d,"]").replace(g,""))){return c(eval("("+q+")"),e);}throw new SyntaxError("JSON.parse");};b.namespace("JSON").parse=function(q,e){if(typeof q!=="string"){q+="";}return l&&b.JSON.useNativeParse?l.parse(q,e):h(q,e);};function a(q,e){return q==="ok"?true:e;}if(l){try{f=(l.parse('{"ok":false}',a)).ok;}catch(i){f=false;}}b.JSON.useNativeParse=f;},"3.4.1",{requires:["yui-base"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("classnamemanager",function(c){var b="classNamePrefix",d="classNameDelimiter",a=c.config;a[b]=a[b]||"yui3";a[d]=a[d]||"-";c.ClassNameManager=function(){var e=a[b],f=a[d];return{getClassName:c.cached(function(){var g=c.Array(arguments);if(g[g.length-1]!==true){g.unshift(e);}else{g.pop();}return g.join(f);})};}();},"3.4.1",{requires:["yui-base"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-base",function(b){var g=b.Lang,r=b.Node,e=b.ClassNameManager,w=e.getClassName,M,s=b.cached(function(L){return L.substring(0,1).toUpperCase()+L.substring(1);}),F="content",P="visible",K="hidden",y="disabled",B="focused",d="width",A="height",N="boundingBox",v="contentBox",k="parentNode",m="ownerDocument",x="auto",j="srcNode",I="body",H="tabIndex",q="id",i="render",J="rendered",n="destroyed",a="strings",o="<div></div>",z="Change",p="loading",E="_uiSet",D="",G=function(){},u=true,O=false,t,l={},f=[P,y,A,d,B],C=b.UA.webkit,h={};function c(Q){var T=this,L,S,R=T.constructor;T._strs={};T._cssPrefix=R.CSS_PREFIX||w(R.NAME.toLowerCase());c.superclass.constructor.apply(T,arguments);S=T.get(i);if(S){if(S!==u){L=S;}T.render(L);}}c.NAME="widget";t=c.UI_SRC="ui";c.ATTRS=l;l[q]={valueFn:"_guid",writeOnce:u};l[J]={value:O,readOnly:u};l[N]={value:null,setter:"_setBB",writeOnce:u};l[v]={valueFn:"_defaultCB",setter:"_setCB",writeOnce:u};l[H]={value:null,validator:"_validTabIndex"};l[B]={value:O,readOnly:u};l[y]={value:O};l[P]={value:u};l[A]={value:D};l[d]={value:D};l[a]={value:{},setter:"_strSetter",getter:"_strGetter"};l[i]={value:O,writeOnce:u};c.CSS_PREFIX=w(c.NAME.toLowerCase());c.getClassName=function(){return w.apply(e,[c.CSS_PREFIX].concat(b.Array(arguments),true));};M=c.getClassName;c.getByNode=function(L){var R,Q=M();L=r.one(L);if(L){L=L.ancestor("."+Q,true);if(L){R=h[b.stamp(L,u)];}}return R||null;};b.extend(c,b.Base,{getClassName:function(){return w.apply(e,[this._cssPrefix].concat(b.Array(arguments),true));},initializer:function(L){h[b.stamp(this.get(N))]=this;if(this._applyParser){this._applyParser(L);}},destructor:function(){var L=this.get(N),Q=b.stamp(L,u);if(Q in h){delete h[Q];}this._destroyBox();},destroy:function(L){this._destroyAllNodes=L;return c.superclass.destroy.apply(this);},_destroyBox:function(){var R=this.get(N),Q=this.get(v),L=this._destroyAllNodes,S=R&&R.compareTo(Q);if(this.UI_EVENTS){this._destroyUIEvents();}this._unbindUI(R);if(L){R.empty();R.remove(u);}else{if(Q){Q.remove(u);}if(!S){R.remove(u);}}},render:function(L){if(!this.get(n)&&!this.get(J)){this.publish(i,{queuable:O,fireOnce:u,defaultTargetOnly:u,defaultFn:this._defRenderFn});this.fire(i,{parentNode:(L)?r.one(L):null});}return this;},_defRenderFn:function(L){this._parentNode=L.parentNode;this.renderer();this._set(J,u);this._removeLoadingClassNames();},renderer:function(){var L=this;L._renderUI();L.renderUI();L._bindUI();L.bindUI();L._syncUI();L.syncUI();},bindUI:G,renderUI:G,syncUI:G,hide:function(){return this.set(P,O);},show:function(){return this.set(P,u);},focus:function(){return this._set(B,u);},blur:function(){return this._set(B,O);},enable:function(){return this.set(y,O);},disable:function(){return this.set(y,u);},_uiSizeCB:function(L){this.get(v).toggleClass(M(F,"expanded"),L);},_renderBox:function(L){var T=this,Q=T.get(v),R=T.get(N),V=T.get(j),S=T.DEF_PARENT_NODE,U=(V&&V.get(m))||R.get(m)||Q.get(m);if(V&&!V.compareTo(Q)&&!Q.inDoc(U)){V.replace(Q);}if(!R.compareTo(Q.get(k))&&!R.compareTo(Q)){if(Q.inDoc(U)){Q.replace(R);}R.appendChild(Q);}L=L||(S&&r.one(S));if(L){L.appendChild(R);}else{if(!R.inDoc(U)){r.one(I).insert(R,0);}}},_setBB:function(L){return this._setBox(this.get(q),L,this.BOUNDING_TEMPLATE);},_setCB:function(L){return(this.CONTENT_TEMPLATE===null)?this.get(N):this._setBox(null,L,this.CONTENT_TEMPLATE);},_defaultCB:function(L){return this.get(j)||null;},_setBox:function(R,Q,L){Q=r.one(Q)||r.create(L);if(!Q.get(q)){Q.set(q,R||b.guid());}return Q;},_renderUI:function(){this._renderBoxClassNames();this._renderBox(this._parentNode);},_renderBoxClassNames:function(){var S=this._getClasses(),L,Q=this.get(N),R;Q.addClass(M());for(R=S.length-3;R>=0;R--){L=S[R];Q.addClass(L.CSS_PREFIX||w(L.NAME.toLowerCase()));}this.get(v).addClass(this.getClassName(F));},_removeLoadingClassNames:function(){var R=this.get(N),L=this.get(v),Q=this.getClassName(p),S=M(p);R.removeClass(S).removeClass(Q);L.removeClass(S).removeClass(Q);},_bindUI:function(){this._bindAttrUI(this._UI_ATTRS.BIND);this._bindDOM();},_unbindUI:function(L){this._unbindDOM(L);},_bindDOM:function(){var L=this.get(N).get(m);this._hDocFocus=L.on("focus",this._onDocFocus,this);if(C){this._hDocMouseDown=L.on("mousedown",this._onDocMouseDown,this);}},_unbindDOM:function(L){if(this._hDocFocus){this._hDocFocus.detach();}if(C&&this._hDocMouseDown){this._hDocMouseDown.detach();}},_syncUI:function(){this._syncAttrUI(this._UI_ATTRS.SYNC);},_uiSetHeight:function(L){this._uiSetDim(A,L);this._uiSizeCB((L!==D&&L!==x));},_uiSetWidth:function(L){this._uiSetDim(d,L);},_uiSetDim:function(L,Q){this.get(N).setStyle(L,g.isNumber(Q)?Q+this.DEF_UNIT:Q);},_uiSetVisible:function(L){this.get(N).toggleClass(this.getClassName(K),!L);},_uiSetDisabled:function(L){this.get(N).toggleClass(this.getClassName(y),L);},_uiSetFocused:function(R,Q){var L=this.get(N);L.toggleClass(this.getClassName(B),R);if(Q!==t){if(R){L.focus();}else{L.blur();}}},_uiSetTabIndex:function(Q){var L=this.get(N);if(g.isNumber(Q)){L.set(H,Q);}else{L.removeAttribute(H);}},_onDocMouseDown:function(L){if(this._domFocus){this._onDocFocus(L);}},_onDocFocus:function(L){this._domFocus=this.get(N).contains(L.target);this._set(B,this._domFocus,{src:t});},toString:function(){return this.name+"["+this.get(q)+"]";},DEF_UNIT:"px",DEF_PARENT_NODE:null,CONTENT_TEMPLATE:o,BOUNDING_TEMPLATE:o,_guid:function(){return b.guid();},_validTabIndex:function(L){return(g.isNumber(L)||g.isNull(L));},_bindAttrUI:function(Q){var R,L=Q.length;for(R=0;R<L;R++){this.after(Q[R]+z,this._setAttrUI);}},_syncAttrUI:function(R){var S,Q=R.length,L;for(S=0;S<Q;S++){L=R[S];this[E+s(L)](this.get(L));}},_setAttrUI:function(L){this[E+s(L.attrName)](L.newVal,L.src);},_strSetter:function(L){return b.merge(this.get(a),L);},getString:function(L){return this.get(a)[L];},getStrings:function(){return this.get(a);},_UI_ATTRS:{BIND:f,SYNC:f.concat(H)}});b.Widget=c;},"3.4.1",{requires:["attribute","event-focus","base-base","base-pluginhost","node-base","node-style","classnamemanager"],skinnable:true});
/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-htmlparser",function(f){var e=f.Widget,c=f.Node,d=f.Lang,a="srcNode",b="contentBox";e.HTML_PARSER={};e._buildCfg={aggregates:["HTML_PARSER"]};e.ATTRS[a]={value:null,setter:c.one,getter:"_getSrcNode",writeOnce:true};f.mix(e.prototype,{_getSrcNode:function(g){return g||this.get(b);},_applyParsedConfig:function(i,g,h){return(h)?f.mix(g,h,false):g;},_applyParser:function(g){var i=this,j=i.get(a),h=i._getHtmlParser(),l,k;if(h&&j){f.Object.each(h,function(n,m,p){k=null;if(d.isFunction(n)){k=n.call(i,j);}else{if(d.isArray(n)){k=j.all(n[0]);if(k.isEmpty()){k=null;}}else{k=j.one(n);}}if(k!==null&&k!==undefined){l=l||{};l[m]=k;}});}g=i._applyParsedConfig(j,g,l);},_getHtmlParser:function(){var h=this._getClasses(),k={},g,j;for(g=h.length-1;g>=0;g--){j=h[g].HTML_PARSER;if(j){f.mix(k,j,true);}}return k;}});},"3.4.1",{requires:["widget-base"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-uievents",function(g){var f="boundingBox",e=g.Widget,d="render",a=g.Lang,c=":",b=g.Widget._uievts=g.Widget._uievts||{};g.mix(e.prototype,{_destroyUIEvents:function(){var h=g.stamp(this,true);g.each(b,function(j,i){if(j.instances[h]){delete j.instances[h];if(g.Object.isEmpty(j.instances)){j.handle.detach();if(b[i]){delete b[i];}}}});},UI_EVENTS:g.Node.DOM_EVENTS,_getUIEventNode:function(){return this.get(f);},_createUIEvent:function(i){var l=this._getUIEventNode(),h=(g.stamp(l)+i),k=b[h],j;if(!k){j=l.delegate(i,function(m){var n=e.getByNode(this);if(n){if(n._filterUIEvent(m)){n.fire(m.type,{domEvent:m});}}},"."+g.Widget.getClassName());b[h]=k={instances:{},handle:j};}k.instances[g.stamp(this)]=1;},_filterUIEvent:function(h){return(h.currentTarget.compareTo(h.container)||h.container.compareTo(this._getUIEventNode()));},_getUIEvent:function(j){if(a.isString(j)){var k=this.parseType(j)[1],h,i;if(k){h=k.indexOf(c);if(h>-1){k=k.substring(h+c.length);}if(this.UI_EVENTS[k]){i=k;}}return i;}},_initUIEvent:function(i){var j=this._getUIEvent(i),h=this._uiEvtsInitQueue||{};if(j&&!h[j]){this._uiEvtsInitQueue=h[j]=1;this.after(d,function(){this._createUIEvent(j);delete this._uiEvtsInitQueue[j];});}},on:function(h){this._initUIEvent(h);return e.superclass.on.apply(this,arguments);},publish:function(i,h){var j=this._getUIEvent(i);if(j&&h&&h.defaultFn){this._initUIEvent(j);}return e.superclass.publish.apply(this,arguments);}},true);},"3.4.1",{requires:["widget-base","node-event-delegate"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-skin",function(e){var d="boundingBox",b="contentBox",a="skin",c=e.ClassNameManager.getClassName;e.Widget.prototype.getSkinName=function(){var f=this.get(b)||this.get(d),h=new RegExp("\\b"+c(a)+"-(\\S+)"),g;if(f){f.ancestor(function(i){g=i.get("className").match(h);return g;});}return(g)?g[1]:null;};},"3.4.1",{requires:["widget-base"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-stdmod",function(b){var f=b.Lang,q=b.Node,x=b.UA,e=b.Widget,d="",J="hd",G="bd",j="ft",D="header",M="body",K="footer",N="fillHeight",m="stdmod",u="Node",I="Content",C="firstChild",h="childNodes",n="ownerDocument",v="contentBox",z="height",F="offsetHeight",y="auto",l="headerContentChange",B="bodyContentChange",o="footerContentChange",r="fillHeightChange",t="heightChange",O="contentUpdate",w="renderUI",E="bindUI",g="syncUI",H="_applyParsedConfig",s=b.Widget.UI_SRC;function P(L){this._stdModNode=this.get(v);b.before(this._renderUIStdMod,this,w);b.before(this._bindUIStdMod,this,E);b.before(this._syncUIStdMod,this,g);}P.HEADER=D;P.BODY=M;P.FOOTER=K;P.AFTER="after";P.BEFORE="before";P.REPLACE="replace";var k=P.HEADER,A=P.BODY,p=P.FOOTER,a=k+I,c=p+I,i=A+I;P.ATTRS={headerContent:{value:null},footerContent:{value:null},bodyContent:{value:null},fillHeight:{value:P.BODY,validator:function(L){return this._validateFillHeight(L);}}};P.HTML_PARSER={headerContent:function(L){return this._parseStdModHTML(k);},bodyContent:function(L){return this._parseStdModHTML(A);},footerContent:function(L){return this._parseStdModHTML(p);}};P.SECTION_CLASS_NAMES={header:e.getClassName(J),body:e.getClassName(G),footer:e.getClassName(j)};P.TEMPLATES={header:'<div class="'+P.SECTION_CLASS_NAMES[k]+'"></div>',body:'<div class="'+P.SECTION_CLASS_NAMES[A]+'"></div>',footer:'<div class="'+P.SECTION_CLASS_NAMES[p]+'"></div>'};P.prototype={_syncUIStdMod:function(){var L=this._stdModParsed;if(!L||!L[a]){this._uiSetStdMod(k,this.get(a));}if(!L||!L[i]){this._uiSetStdMod(A,this.get(i));}if(!L||!L[c]){this._uiSetStdMod(p,this.get(c));}this._uiSetFillHeight(this.get(N));},_renderUIStdMod:function(){this._stdModNode.addClass(e.getClassName(m));this._renderStdModSections();this.after(l,this._afterHeaderChange);this.after(B,this._afterBodyChange);this.after(o,this._afterFooterChange);},_renderStdModSections:function(){if(f.isValue(this.get(a))){this._renderStdMod(k);}if(f.isValue(this.get(i))){this._renderStdMod(A);}if(f.isValue(this.get(c))){this._renderStdMod(p);}},_bindUIStdMod:function(){this.after(r,this._afterFillHeightChange);this.after(t,this._fillHeight);this.after(O,this._fillHeight);},_afterHeaderChange:function(L){if(L.src!==s){this._uiSetStdMod(k,L.newVal,L.stdModPosition);}},_afterBodyChange:function(L){if(L.src!==s){this._uiSetStdMod(A,L.newVal,L.stdModPosition);}},_afterFooterChange:function(L){if(L.src!==s){this._uiSetStdMod(p,L.newVal,L.stdModPosition);}},_afterFillHeightChange:function(L){this._uiSetFillHeight(L.newVal);},_validateFillHeight:function(L){return !L||L==P.BODY||L==P.HEADER||L==P.FOOTER;},_uiSetFillHeight:function(R){var Q=this.getStdModNode(R);var L=this._currFillNode;if(L&&Q!==L){L.setStyle(z,d);}if(Q){this._currFillNode=Q;}this._fillHeight();},_fillHeight:function(){if(this.get(N)){var L=this.get(z);if(L!=d&&L!=y){this.fillHeight(this._currFillNode);}}},_uiSetStdMod:function(S,R,L){if(f.isValue(R)){var Q=this.getStdModNode(S)||this._renderStdMod(S);this._addStdModContent(Q,R,L);this.set(S+I,this._getStdModContent(S),{src:s});}else{this._eraseStdMod(S);}this.fire(O);},_renderStdMod:function(R){var L=this.get(v),Q=this._findStdModSection(R);if(!Q){Q=this._getStdModTemplate(R);}this._insertStdModSection(L,R,Q);this[R+u]=Q;return this[R+u];},_eraseStdMod:function(Q){var L=this.getStdModNode(Q);if(L){L.remove(true);delete this[Q+u];}},_insertStdModSection:function(L,S,R){var Q=L.get(C);if(S===p||!Q){L.appendChild(R);}else{if(S===k){L.insertBefore(R,Q);}else{var T=this[p+u];if(T){L.insertBefore(R,T);}else{L.appendChild(R);}}}},_getStdModTemplate:function(L){return q.create(P.TEMPLATES[L],this._stdModNode.get(n));},_addStdModContent:function(R,Q,L){switch(L){case P.BEFORE:L=0;break;case P.AFTER:L=undefined;break;default:L=P.REPLACE;}R.insert(Q,L);},_getPreciseHeight:function(R){var L=(R)?R.get(F):0,S="getBoundingClientRect";if(R&&R.hasMethod(S)){var Q=R.invoke(S);if(Q){L=Q.bottom-Q.top;}}return L;},_findStdModSection:function(L){return this.get(v).one("> ."+P.SECTION_CLASS_NAMES[L]);},_parseStdModHTML:function(Q){var L=this._findStdModSection(Q);if(L){if(!this._stdModParsed){this._stdModParsed={};b.before(this._applyStdModParsedConfig,this,H);}this._stdModParsed[Q+I]=1;return L.get("innerHTML");}return null;},_applyStdModParsedConfig:function(S,L,R){var Q=this._stdModParsed;if(Q){Q[a]=!(a in L)&&(a in Q);Q[i]=!(i in L)&&(i in Q);Q[c]=!(c in L)&&(c in Q);}},_getStdModContent:function(L){return(this[L+u])?this[L+u].get(h):null;},setStdModContent:function(R,Q,L){this.set(R+I,Q,{stdModPosition:L});},getStdModNode:function(L){return this[L+u]||null;},fillHeight:function(Q){if(Q){var V=this.get(v),W=[this.headerNode,this.bodyNode,this.footerNode],L,X,Y=0,T=0,S=false;for(var U=0,R=W.length;U<R;U++){L=W[U];if(L){if(L!==Q){Y+=this._getPreciseHeight(L);}else{S=true;}}}if(S){if(x.ie||x.opera){Q.set(F,0);}X=V.get(F)-parseInt(V.getComputedStyle("paddingTop"),10)-parseInt(V.getComputedStyle("paddingBottom"),10)-parseInt(V.getComputedStyle("borderBottomWidth"),10)-parseInt(V.getComputedStyle("borderTopWidth"),10);if(f.isNumber(X)){T=X-Y;if(T>=0){Q.set(F,T);}}}}}};b.WidgetStdMod=P;},"3.4.1",{requires:["base-build","widget"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-position",function(a){var i=a.Lang,l=a.Widget,n="xy",j="position",g="positioned",k="boundingBox",h="relative",m="renderUI",f="bindUI",d="syncUI",c=l.UI_SRC,e="xyChange";function b(o){this._posNode=this.get(k);a.after(this._renderUIPosition,this,m);a.after(this._syncUIPosition,this,d);a.after(this._bindUIPosition,this,f);}b.ATTRS={x:{setter:function(o){this._setX(o);},getter:function(){return this._getX();},lazyAdd:false},y:{setter:function(o){this._setY(o);},getter:function(){return this._getY();},lazyAdd:false},xy:{value:[0,0],validator:function(o){return this._validateXY(o);}}};b.POSITIONED_CLASS_NAME=l.getClassName(g);b.prototype={_renderUIPosition:function(){this._posNode.addClass(b.POSITIONED_CLASS_NAME);},_syncUIPosition:function(){var o=this._posNode;if(o.getStyle(j)===h){this.syncXY();}this._uiSetXY(this.get(n));},_bindUIPosition:function(){this.after(e,this._afterXYChange);},move:function(){var o=arguments,p=(i.isArray(o[0]))?o[0]:[o[0],o[1]];this.set(n,p);},syncXY:function(){this.set(n,this._posNode.getXY(),{src:c});},_validateXY:function(o){return(i.isArray(o)&&i.isNumber(o[0])&&i.isNumber(o[1]));},_setX:function(o){this.set(n,[o,this.get(n)[1]]);},_setY:function(o){this.set(n,[this.get(n)[0],o]);},_getX:function(){return this.get(n)[0];},_getY:function(){return this.get(n)[1];},_afterXYChange:function(o){if(o.src!=c){this._uiSetXY(o.newVal);}},_uiSetXY:function(o){this._posNode.setXY(o);}};a.WidgetPosition=b;},"3.4.1",{requires:["base-build","node-screen","widget"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-position-align",function(a){var f=a.Lang,d="align",b="alignOn",g="visible",i="boundingBox",e="offsetWidth",j="offsetHeight",h="region",k="viewportRegion";function c(l){if(!this._posNode){a.error("WidgetPosition needs to be added to the Widget, "+"before WidgetPositionAlign is added");}a.after(this._bindUIPosAlign,this,"bindUI");a.after(this._syncUIPosAlign,this,"syncUI");}c.ATTRS={align:{value:null},centered:{setter:"_setAlignCenter",lazyAdd:false,value:false},alignOn:{value:[],validator:a.Lang.isArray}};c.TL="tl";c.TR="tr";c.BL="bl";c.BR="br";c.TC="tc";c.RC="rc";c.BC="bc";c.LC="lc";c.CC="cc";c.prototype={_posAlignUIHandles:null,destructor:function(){this._detachPosAlignUIHandles();},_bindUIPosAlign:function(){this.after("alignChange",this._afterAlignChange);this.after("alignOnChange",this._afterAlignOnChange);this.after("visibleChange",this._syncUIPosAlign);},_syncUIPosAlign:function(){var l=this.get(d);this._uiSetVisiblePosAlign(this.get(g));if(l){this._uiSetAlign(l.node,l.points);}},align:function(m,l){if(arguments.length){this.set(d,{node:m,points:l});}else{this._syncUIPosAlign();}return this;},centered:function(l){return this.align(l,[c.CC,c.CC]);},_setAlignCenter:function(l){if(l){this.set(d,{node:l===true?null:l,points:[c.CC,c.CC]});}return l;},_uiSetAlign:function(o,n){if(!f.isArray(n)||n.length!==2){a.error("align: Invalid Points Arguments");return;}var m=this._getRegion(o),l,p,q;if(!m){return;}l=n[0];p=n[1];switch(p){case c.TL:q=[m.left,m.top];break;case c.TR:q=[m.right,m.top];break;case c.BL:q=[m.left,m.bottom];break;case c.BR:q=[m.right,m.bottom];break;case c.TC:q=[m.left+Math.floor(m.width/2),m.top];break;case c.BC:q=[m.left+Math.floor(m.width/2),m.bottom];break;case c.LC:q=[m.left,m.top+Math.floor(m.height/2)];break;case c.RC:q=[m.right,m.top+Math.floor(m.height/2)];break;case c.CC:q=[m.left+Math.floor(m.width/2),m.top+Math.floor(m.height/2)];break;default:break;}if(q){this._doAlign(l,q[0],q[1]);}},_uiSetVisiblePosAlign:function(l){if(l){this._attachPosAlignUIHandles();}else{this._detachPosAlignUIHandles();}},_attachPosAlignUIHandles:function(){if(this._posAlignUIHandles){return;}var n=this.get(i),m=a.bind(this._syncUIPosAlign,this),l=[];a.Array.each(this.get(b),function(r){var q=r.eventName,p=a.one(r.node)||n;if(q){l.push(p.on(q,m));}});this._posAlignUIHandles=l;},_detachPosAlignUIHandles:function(){var l=this._posAlignUIHandles;if(l){new a.EventHandle(l).detach();this._posAlignUIHandles=null;}},_doAlign:function(m,l,p){var o=this._posNode,n;switch(m){case c.TL:n=[l,p];break;case c.TR:n=[l-o.get(e),p];break;case c.BL:n=[l,p-o.get(j)];break;case c.BR:n=[l-o.get(e),p-o.get(j)];break;case c.TC:n=[l-(o.get(e)/2),p];break;case c.BC:n=[l-(o.get(e)/2),p-o.get(j)];break;case c.LC:n=[l,p-(o.get(j)/2)];break;case c.RC:n=[l-o.get(e),p-(o.get(j)/2)];break;case c.CC:n=[l-(o.get(e)/2),p-(o.get(j)/2)];break;default:break;}if(n){this.move(n);}},_getRegion:function(m){var l;if(!m){l=this._posNode.get(k);}else{m=a.Node.one(m);if(m){l=m.get(h);}}return l;},_afterAlignChange:function(l){var m=l.newVal;if(m){this._uiSetAlign(m.node,m.points);}},_afterAlignOnChange:function(l){this._detachPosAlignUIHandles();if(this.get(g)){this._attachPosAlignUIHandles();}}};a.WidgetPositionAlign=c;},"3.4.1",{requires:["widget-position"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-stack",function(e){var m=e.Lang,s=e.UA,B=e.Node,f=e.Widget,A="zIndex",o="shim",y="visible",C="boundingBox",v="renderUI",g="bindUI",r="syncUI",p="offsetWidth",d="offsetHeight",l="parentNode",a="firstChild",w="ownerDocument",h="width",u="height",k="px",n="shimdeferred",D="shimresize",x="visibleChange",c="widthChange",j="heightChange",z="shimChange",b="zIndexChange",i="contentUpdate",q="stacked";function t(E){this._stackNode=this.get(C);this._stackHandles={};e.after(this._renderUIStack,this,v);e.after(this._syncUIStack,this,r);e.after(this._bindUIStack,this,g);}t.ATTRS={shim:{value:(s.ie==6)},zIndex:{value:1,setter:function(E){return this._setZIndex(E);}}};t.HTML_PARSER={zIndex:function(E){return E.getStyle(A);}};t.SHIM_CLASS_NAME=f.getClassName(o);t.STACKED_CLASS_NAME=f.getClassName(q);t.SHIM_TEMPLATE='<iframe class="'+t.SHIM_CLASS_NAME+'" frameborder="0" title="Widget Stacking Shim" src="javascript:false" tabindex="-1" role="presentation"></iframe>';t.prototype={_syncUIStack:function(){this._uiSetShim(this.get(o));this._uiSetZIndex(this.get(A));},_bindUIStack:function(){this.after(z,this._afterShimChange);this.after(b,this._afterZIndexChange);},_renderUIStack:function(){this._stackNode.addClass(t.STACKED_CLASS_NAME);},_setZIndex:function(E){if(m.isString(E)){E=parseInt(E,10);}if(!m.isNumber(E)){E=0;}return E;},_afterShimChange:function(E){this._uiSetShim(E.newVal);},_afterZIndexChange:function(E){this._uiSetZIndex(E.newVal);},_uiSetZIndex:function(E){this._stackNode.setStyle(A,E);},_uiSetShim:function(E){if(E){if(this.get(y)){this._renderShim();}else{this._renderShimDeferred();}if(s.ie==6){this._addShimResizeHandlers();}}else{this._destroyShim();}},_renderShimDeferred:function(){this._stackHandles[n]=this._stackHandles[n]||[];var F=this._stackHandles[n],E=function(G){if(G.newVal){this._renderShim();}};F.push(this.on(x,E));},_addShimResizeHandlers:function(){this._stackHandles[D]=this._stackHandles[D]||[];var F=this.sizeShim,E=this._stackHandles[D];E.push(this.after(x,F));E.push(this.after(c,F));E.push(this.after(j,F));E.push(this.after(i,F));},_detachStackHandles:function(E){var F=this._stackHandles[E],G;if(F&&F.length>0){while((G=F.pop())){G.detach();}}},_renderShim:function(){var E=this._shimNode,F=this._stackNode;if(!E){E=this._shimNode=this._getShimTemplate();F.insertBefore(E,F.get(a));this._detachStackHandles(n);this.sizeShim();}},_destroyShim:function(){if(this._shimNode){this._shimNode.get(l).removeChild(this._shimNode);this._shimNode=null;this._detachStackHandles(n);this._detachStackHandles(D);}},sizeShim:function(){var F=this._shimNode,E=this._stackNode;if(F&&s.ie===6&&this.get(y)){F.setStyle(h,E.get(p)+k);F.setStyle(u,E.get(d)+k);}},_getShimTemplate:function(){return B.create(t.SHIM_TEMPLATE,this._stackNode.get(w));}};e.WidgetStack=t;},"3.4.1",{requires:["base-build","widget"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("widget-position-constrain",function(c){var f="constrain",d="constrain|xyChange",b="constrainChange",n="preventOverlap",e="align",o="",g="bindUI",i="xy",a="x",m="y",j=c.Node,p="viewportRegion",l="region",k;function h(q){if(!this._posNode){c.error("WidgetPosition needs to be added to the Widget, before WidgetPositionConstrain is added");}c.after(this._bindUIPosConstrained,this,g);}h.ATTRS={constrain:{value:null,setter:"_setConstrain"},preventOverlap:{value:false}};k=h._PREVENT_OVERLAP={x:{"tltr":1,"blbr":1,"brbl":1,"trtl":1},y:{"trbr":1,"tlbl":1,"bltl":1,"brtr":1}};h.prototype={getConstrainedXY:function(t,s){s=s||this.get(f);var r=this._getRegion((s===true)?null:s),q=this._posNode.get(l);return[this._constrain(t[0],a,q,r),this._constrain(t[1],m,q,r)];},constrain:function(u,r){var t,q,s=r||this.get(f);if(s){t=u||this.get(i);q=this.getConstrainedXY(t,s);if(q[0]!==t[0]||q[1]!==t[1]){this.set(i,q,{constrained:true});}}},_setConstrain:function(q){return(q===true)?q:j.one(q);},_constrain:function(q,r,z,s){if(s){if(this.get(n)){q=this._preventOverlap(q,r,z,s);}var v=(r==a),y=(v)?s.width:s.height,u=(v)?z.width:z.height,t=(v)?s.left:s.top,w=(v)?s.right-u:s.bottom-u;if(q<t||q>w){if(u<y){if(q<t){q=t;}else{if(q>w){q=w;}}}else{q=t;}}}return q;},_preventOverlap:function(r,s,C,t){var w=this.get(e),B=(s===a),z,v,u,y,A,q;if(w&&w.points&&k[s][w.points.join(o)]){v=this._getRegion(w.node);if(v){z=(B)?C.width:C.height;u=(B)?v.left:v.top;y=(B)?v.right:v.bottom;A=(B)?v.left-t.left:v.top-t.top;q=(B)?t.right-v.right:t.bottom-v.bottom;}if(r>u){if(q<z&&A>z){r=u-z;}}else{if(A<z&&q>z){r=y;}}}return r;},_bindUIPosConstrained:function(){this.after(b,this._afterConstrainChange);this._enableConstraints(this.get(f));},_afterConstrainChange:function(q){this._enableConstraints(q.newVal);},_enableConstraints:function(q){if(q){this.constrain();this._cxyHandle=this._cxyHandle||this.on(d,this._constrainOnXYChange);}else{if(this._cxyHandle){this._cxyHandle.detach();this._cxyHandle=null;}}},_constrainOnXYChange:function(q){if(!q.constrained){q.newVal=this.getConstrainedXY(q.newVal);}},_getRegion:function(q){var r;if(!q){r=this._posNode.get(p);}else{q=j.one(q);if(q){r=q.get(l);}}return r;}};c.WidgetPositionConstrain=h;},"3.4.1",{requires:["widget-position"]});/*
YUI 3.4.1 (build 4118)
Copyright 2011 Yahoo! Inc. All rights reserved.
Licensed under the BSD License.
http://yuilibrary.com/license/
*/
YUI.add("overlay",function(a){a.Overlay=a.Base.create("overlay",a.Widget,[a.WidgetStdMod,a.WidgetPosition,a.WidgetStack,a.WidgetPositionAlign,a.WidgetPositionConstrain]);},"3.4.1",{requires:["widget","widget-stdmod","widget-position","widget-stack","widget-position-align","widget-position-constrain"]});YUI.add('moodle-enrol-notification', function(Y) {

var DIALOGUE_NAME = 'Moodle dialogue',
    DIALOGUE_PREFIX = 'moodle-dialogue',
    CONFIRM_NAME = 'Moodle confirmation dialogue',
    EXCEPTION_NAME = 'Moodle exception',
    AJAXEXCEPTION_NAME = 'Moodle AJAX exception',
    ALERT_NAME = 'Moodle alert',
    C = Y.Node.create,
    BASE = 'notificationBase',
    LIGHTBOX = 'lightbox',
    NODELIGHTBOX = 'nodeLightbox',
    COUNT = 0,
    CONFIRMYES = 'yesLabel',
    CONFIRMNO = 'noLabel',
    TITLE = 'title',
    QUESTION = 'question',
    CSS = {
        BASE : 'moodle-dialogue-base',
        WRAP : 'moodle-dialogue-wrap',
        HEADER : 'moodle-dialogue-hd',
        BODY : 'moodle-dialogue-bd',
        CONTENT : 'moodle-dialogue-content',
        FOOTER : 'moodle-dialogue-ft',
        HIDDEN : 'hidden',
        LIGHTBOX : 'moodle-dialogue-lightbox'
    };

var DIALOGUE = function(config) {
    COUNT++;
    var id = 'moodle-dialogue-'+COUNT;
    config.notificationBase =
        C('<div class="'+CSS.BASE+'">')
            .append(C('<div class="'+CSS.LIGHTBOX+' '+CSS.HIDDEN+'"></div>'))
            .append(C('<div id="'+id+'" class="'+CSS.WRAP+'"></div>')
                .append(C('<div class="'+CSS.HEADER+' yui3-widget-hd"></div>'))
                .append(C('<div class="'+CSS.BODY+' yui3-widget-bd"></div>'))
                .append(C('<div class="'+CSS.FOOTER+' yui3-widget-ft"></div>')));
    Y.one(document.body).append(config.notificationBase);
    config.srcNode =    '#'+id;
    config.width =      config.width || '400px';
    config.visible =    config.visible || false;
    config.center =     config.centered || true;
    config.centered =   false;
    DIALOGUE.superclass.constructor.apply(this, [config]);
};
Y.extend(DIALOGUE, Y.Overlay, {
    initializer : function(config) {
        this.set(NODELIGHTBOX, this.get(BASE).one('.'+CSS.LIGHTBOX).setStyle('opacity', 0.5));
        this.after('visibleChange', this.visibilityChanged, this);
        this.after('headerContentChange', function(e){
            var h = (this.get('closeButton'))?this.get(BASE).one('.'+CSS.HEADER):false;
            if (h && !h.one('.closebutton')) {
                var c = C('<div class="closebutton"></div>');
                c.on('click', this.hide, this);
                h.append(c);
            }
        }, this);
        this.render();
        this.show();
    },
    visibilityChanged : function(e) {
        switch (e.attrName) {
            case 'visible':
                if (this.get(LIGHTBOX)) {
                    var l = this.get(NODELIGHTBOX);
                    if (!e.prevVal && e.newVal) {
                        l.setStyle('height',l.get('docHeight')+'px').removeClass(CSS.HIDDEN);
                    } else if (e.prevVal && !e.newVal) {
                        l.addClass(CSS.HIDDEN);
                    }
                }
                if (this.get('center') && !e.prevVal && e.newVal) {
                    this.centerDialogue();
                }
                break;
        }
    },
    centerDialogue : function() {
        var bb = this.get('boundingBox'), hidden = bb.hasClass(DIALOGUE_PREFIX+'-hidden');
        if (hidden) {
            bb.setStyle('top', '-1000px').removeClass(DIALOGUE_PREFIX+'-hidden');
        }
        var x = Math.max(Math.round((bb.get('winWidth') - bb.get('offsetWidth'))/2), 15);
        var y = Math.max(Math.round((bb.get('winHeight') - bb.get('offsetHeight'))/2), 15) + Y.one(window).get('scrollTop');

        if (hidden) {
            bb.addClass(DIALOGUE_PREFIX+'-hidden');
        }
        bb.setStyle('left', x).setStyle('top', y);
    }
}, {
    NAME : DIALOGUE_NAME,
    CSS_PREFIX : DIALOGUE_PREFIX,
    ATTRS : {
        notificationBase : {

        },
        nodeLightbox : {
            value : null
        },
        lightbox : {
            validator : Y.Lang.isBoolean,
            value : true
        },
        closeButton : {
            validator : Y.Lang.isBoolean,
            value : true
        },
        center : {
            validator : Y.Lang.isBoolean,
            value : true
        }
    }
});

var ALERT = function(config) {
    config.closeButton = false;
    ALERT.superclass.constructor.apply(this, [config]);
};
Y.extend(ALERT, DIALOGUE, {
    _enterKeypress : null,
    initializer : function(config) {
        this.publish('complete');
        var yes = C('<input type="button" value="'+this.get(CONFIRMYES)+'" />'),
            content = C('<div class="confirmation-dialogue"></div>')
                    .append(C('<div class="confirmation-message">'+this.get('message')+'</div>'))
                    .append(C('<div class="confirmation-buttons"></div>')
                            .append(yes));
        this.get(BASE).addClass('moodle-dialogue-confirm');
        this.setStdModContent(Y.WidgetStdMod.BODY, content, Y.WidgetStdMod.REPLACE);
        this.setStdModContent(Y.WidgetStdMod.HEADER, this.get(TITLE), Y.WidgetStdMod.REPLACE);
        this.after('destroyedChange', function(){this.get(BASE).remove();}, this);
        this._enterKeypress = Y.on('key', this.submit, window, 'down:13', this);
        yes.on('click', this.submit, this);
    },
    submit : function(e, outcome) {
        this._enterKeypress.detach();
        this.fire('complete');
        this.hide();
        this.destroy();
    }
}, {
    NAME : ALERT_NAME,
    CSS_PREFIX : DIALOGUE_PREFIX,
    ATTRS : {
        title : {
            validator : Y.Lang.isString,
            value : 'Alert'
        },
        message : {
            validator : Y.Lang.isString,
            value : 'Confirm'
        },
        yesLabel : {
            validator : Y.Lang.isString,
            setter : function(txt) {
                if (!txt) {
                    txt = 'Ok';
                }
                return txt;
            },
            value : 'Ok'
        }
    }
});

var CONFIRM = function(config) {
    CONFIRM.superclass.constructor.apply(this, [config]);
};
Y.extend(CONFIRM, DIALOGUE, {
    _enterKeypress : null,
    _escKeypress : null,
    initializer : function(config) {
        this.publish('complete');
        this.publish('complete-yes');
        this.publish('complete-no');
        var yes = C('<input type="button" value="'+this.get(CONFIRMYES)+'" />'),
            no = C('<input type="button" value="'+this.get(CONFIRMNO)+'" />'),
            content = C('<div class="confirmation-dialogue"></div>')
                        .append(C('<div class="confirmation-message">'+this.get(QUESTION)+'</div>'))
                        .append(C('<div class="confirmation-buttons"></div>')
                            .append(yes)
                            .append(no));
        this.get(BASE).addClass('moodle-dialogue-confirm');
        this.setStdModContent(Y.WidgetStdMod.BODY, content, Y.WidgetStdMod.REPLACE);
        this.setStdModContent(Y.WidgetStdMod.HEADER, this.get(TITLE), Y.WidgetStdMod.REPLACE);
        this.after('destroyedChange', function(){this.get(BASE).remove();}, this);
        this._enterKeypress = Y.on('key', this.submit, window, 'down:13', this, true);
        this._escKeypress = Y.on('key', this.submit, window, 'down:27', this, false);
        yes.on('click', this.submit, this, true);
        no.on('click', this.submit, this, false);
    },
    submit : function(e, outcome) {
        this._enterKeypress.detach();
        this._escKeypress.detach();
        this.fire('complete', outcome);
        if (outcome) {
            this.fire('complete-yes');
        } else {
            this.fire('complete-no');
        }
        this.hide();
        this.destroy();
    }
}, {
    NAME : CONFIRM_NAME,
    CSS_PREFIX : DIALOGUE_PREFIX,
    ATTRS : {
        yesLabel : {
            validator : Y.Lang.isString,
            value : 'Yes'
        },
        noLabel : {
            validator : Y.Lang.isString,
            value : 'No'
        },
        title : {
            validator : Y.Lang.isString,
            value : 'Confirm'
        },
        question : {
            validator : Y.Lang.isString,
            value : 'Are you sure?'
        }
    }
});
Y.augment(CONFIRM, Y.EventTarget);

var EXCEPTION = function(config) {
    config.width = config.width || (M.cfg.developerdebug)?Math.floor(Y.one(document.body).get('winWidth')/3)+'px':null;
    config.closeButton = true;
    EXCEPTION.superclass.constructor.apply(this, [config]);
};
Y.extend(EXCEPTION, DIALOGUE, {
    _hideTimeout : null,
    _keypress : null,
    initializer : function(config) {
        this.get(BASE).addClass('moodle-dialogue-exception');
        this.setStdModContent(Y.WidgetStdMod.HEADER, config.name, Y.WidgetStdMod.REPLACE);
        var content = C('<div class="moodle-exception"></div>')
                    .append(C('<div class="moodle-exception-message">'+this.get('message')+'</div>'))
                    .append(C('<div class="moodle-exception-param hidden param-filename"><label>File:</label> '+this.get('fileName')+'</div>'))
                    .append(C('<div class="moodle-exception-param hidden param-linenumber"><label>Line:</label> '+this.get('lineNumber')+'</div>'))
                    .append(C('<div class="moodle-exception-param hidden param-stacktrace"><label>Stack trace:</label> <pre>'+this.get('stack')+'</pre></div>'));
        if (M.cfg.developerdebug) {
            content.all('.moodle-exception-param').removeClass('hidden');
        }
        this.setStdModContent(Y.WidgetStdMod.BODY, content, Y.WidgetStdMod.REPLACE);

        var self = this;
        var delay = this.get('hideTimeoutDelay');
        if (delay) {
            this._hideTimeout = setTimeout(function(){self.hide();}, delay);
        }
        this.after('visibleChange', this.visibilityChanged, this);
        this.after('destroyedChange', function(){this.get(BASE).remove();}, this);
        this._keypress = Y.on('key', this.hide, window, 'down:13,27', this);
        this.centerDialogue();
    },
    visibilityChanged : function(e) {
        if (e.attrName == 'visible' && e.prevVal && !e.newVal) {
            if (this._keypress) this._keypress.detach();
            var self = this;
            setTimeout(function(){self.destroy();}, 1000);
        }
    }
}, {
    NAME : EXCEPTION_NAME,
    CSS_PREFIX : DIALOGUE_PREFIX,
    ATTRS : {
        message : {
            value : ''
        },
        name : {
            value : ''
        },
        fileName : {
            value : ''
        },
        lineNumber : {
            value : ''
        },
        stack : {
            setter : function(str) {
                var lines = str.split("\n");
                var pattern = new RegExp('^(.+)@('+M.cfg.wwwroot+')?(.{0,75}).*:(\\d+)$');
                for (var i in lines) {
                    lines[i] = lines[i].replace(pattern, "<div class='stacktrace-line'>ln: $4</div><div class='stacktrace-file'>$3</div><div class='stacktrace-call'>$1</div>");
                }
                return lines.join('');
            },
            value : ''
        },
        hideTimeoutDelay : {
            validator : Y.Lang.isNumber,
            value : null
        }
    }
});

var AJAXEXCEPTION = function(config) {
    config.name = config.name || 'Error';
    config.closeButton = true;
    AJAXEXCEPTION.superclass.constructor.apply(this, [config]);
};
Y.extend(AJAXEXCEPTION, DIALOGUE, {
    _keypress : null,
    initializer : function(config) {
        this.get(BASE).addClass('moodle-dialogue-exception');
        this.setStdModContent(Y.WidgetStdMod.HEADER, config.name, Y.WidgetStdMod.REPLACE);
        var content = C('<div class="moodle-ajaxexception"></div>')
                    .append(C('<div class="moodle-exception-message">'+this.get('error')+'</div>'))
                    .append(C('<div class="moodle-exception-param hidden param-debuginfo"><label>URL:</label> '+this.get('reproductionlink')+'</div>'))
                    .append(C('<div class="moodle-exception-param hidden param-debuginfo"><label>Debug info:</label> '+this.get('debuginfo')+'</div>'))
                    .append(C('<div class="moodle-exception-param hidden param-stacktrace"><label>Stack trace:</label> <pre>'+this.get('stacktrace')+'</pre></div>'));
        if (M.cfg.developerdebug) {
            content.all('.moodle-exception-param').removeClass('hidden');
        }
        this.setStdModContent(Y.WidgetStdMod.BODY, content, Y.WidgetStdMod.REPLACE);

        var self = this;
        var delay = this.get('hideTimeoutDelay');
        if (delay) {
            this._hideTimeout = setTimeout(function(){self.hide();}, delay);
        }
        this.after('visibleChange', this.visibilityChanged, this);
        this._keypress = Y.on('key', this.hide, window, 'down:13, 27', this);
        this.centerDialogue();
    },
    visibilityChanged : function(e) {
        if (e.attrName == 'visible' && e.prevVal && !e.newVal) {
            var self = this;
            this._keypress.detach();
            setTimeout(function(){self.destroy();}, 1000);
        }
    }
}, {
    NAME : AJAXEXCEPTION_NAME,
    CSS_PREFIX : DIALOGUE_PREFIX,
    ATTRS : {
        error : {
            validator : Y.Lang.isString,
            value : 'Unknown error'
        },
        debuginfo : {
            value : null
        },
        stacktrace : {
            value : null
        },
        reproductionlink : {
            setter : function(link) {
                if (link !== null) {
                    link = '<a href="'+link+'">'+link.replace(M.cfg.wwwroot, '')+'</a>';
                }
                return link;
            },
            value : null
        },
        hideTimeoutDelay : {
            validator : Y.Lang.isNumber,
            value : null
        }
    }
});

M.core = M.core || {};
M.core.dialogue = DIALOGUE;
M.core.alert = ALERT;
M.core.confirm = CONFIRM;
M.core.exception = EXCEPTION;
M.core.ajaxException = AJAXEXCEPTION;

}, '@VERSION@', {requires:['base','node','overlay','event-key', 'moodle-enrol-notification-skin']});
