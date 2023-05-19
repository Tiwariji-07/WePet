/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Partial.onReady = function() {
    /*
     * variables can be accessed through 'Partial.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Partial.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Partial.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Partial.Widgets.username.datavalue'
     */
};

Partial.AnimalLiveForm1Beforeservicecall = function($event, $operation, $data, options) {
    debugger;
    if (Page.Variables.FileServiceUploadFile2.dataSet.length > 0) {
        $data.imagePath = Page.Variables.FileServiceUploadFile2.dataSet[0].path;
    }
};