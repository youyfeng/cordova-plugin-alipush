var exec = cordova.require('cordova/exec'); // eslint-disable-line no-undef

module.exports = {
	init: function(success, error){
		exec(success, error, "AliPushPlugin", "_init", []);
	},
	getDeviceId: function(success, error) {
		exec(success, error, "AliPushPlugin", "getDeviceId", []);
	},
	bindAccount: function(success, error, account) {
		exec(success, error, "AliPushPlugin", "bindAccount", [account]);
	},
	unbindAccount: function(success, error) {
		exec(success, error, "AliPushPlugin", "unbindAccount", []);
	},
	// {target:Number, tags:Array<String>, alias?:string}
	bindTag: function(success, error, args) {
		exec(success, error, "AliPushPlugin", "bindTag", [args.target,args.tags,args.alias]);
	},
	unbindTag: function(success, error, args) {
		exec(success, error, "AliPushPlugin", "unbindTag", [args.target,args.tags,args.alias]);
	},
	listTags: function(success, error) {
		exec(success, error, "AliPushPlugin", "listTags", []);
	},
	addAlias: function(success, error, alias) {
		exec(success, error, "AliPushPlugin", "addAlias", [alias]);
	},
	removeAlias: function(success, error, alias) {
		exec(success, error, "AliPushPlugin", "removeAlias", [alias]);
	},
	listAliases: function(success, error) {
		exec(success, error, "AliPushPlugin", "listAliases", []);
	},

}