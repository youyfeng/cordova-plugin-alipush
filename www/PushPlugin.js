
var AliPushPlugin = function() {};

AliPushPlugin.prototype.init = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "_init", []);
};
AliPushPlugin.prototype.getDeviceId = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "getDeviceId", []);
};
// (account,success,fail);
AliPushPlugin.prototype.bindAccount = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "bindAccount", [options]);
};
AliPushPlugin.prototype.unbindAccount = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "unbindAccount", []);
};
// {target:Number, tags:Array<String>, alias?:string}
AliPushPlugin.prototype.bindTag = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "bindTag", [options.target,options.tags,options.alias]);
};
AliPushPlugin.prototype.unbindTag = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "unbindTag", [options.target,options.tags,options.alias]);
};
AliPushPlugin.prototype.listTags = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "listTags", []);
};
AliPushPlugin.prototype.addAlias = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "addAlias", [options]);
};
AliPushPlugin.prototype.removeAlias = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "removeAlias", [options]);
};
AliPushPlugin.prototype.listAliases = function(options,success, fail) {
	return cordova.exec(success, fail, "AliPushPlugin", "listAliases", []);
};





window.pushPlugin = new PushPlugin();