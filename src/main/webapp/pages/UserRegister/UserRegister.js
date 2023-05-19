/* perform any action on widgets within this block */
Page.onReady = function() {
    /*
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
};

Page.UserLiveForm1Beforeservicecall = function($event, $operation, $data, options) {
    debugger;
    if (Page.Variables.FileServiceUploadFile5.dataSet.length > 0) {
        $data.imageUrl = Page.Variables.FileServiceUploadFile5.dataSet[0].path;
    }
};
Page.OrganizationLiveForm1Beforeservicecall = function($event, $operation, $data, options) {
    debugger;
    if (Page.Variables.FileServiceUploadFile7.dataSet.length > 0) {
        $data.profilePic = Page.Variables.FileServiceUploadFile7.dataSet[0].path;
    }
    debugger;
    if (Page.Variables.FileServiceUploadFile6.dataSet3.length > 0) {
        $data.license = Page.Variables.FileServiceUploadFile6.dataSet[0].path;
    }
};