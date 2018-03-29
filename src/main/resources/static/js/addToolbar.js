/**
 * Created with WebStorm && Sublime Text 3
 * Date: 2015/10/29 14:04
 */
window.onload = function() {
	bicycleMap.plugin(["AMap.ToolBar"], function() {
		bicycleMap.addControl(new AMap.ToolBar());
	});
	if(location.href.indexOf('&guide=1')!==-1){
		bicycleMap.setStatus({scrollWheel:false})
	}
}