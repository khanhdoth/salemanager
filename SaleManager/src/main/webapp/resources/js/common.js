var jDlgContact;
var uText="createContactForm:pgContact";

onload = function() {    
    //alert('loaded');
    jDlgContact = new MyDialog(wDlgContact);
};

function MyDialog(iDialog){
    this.dialog = iDialog;
    this.isOpen = false;    
    this.uString = "createContactForm:pgContact";
    
    this.onHide=onHide;
    function onHide(){
        this.isOpen = false;
        //alert('onHide: ' +  this.isOpen);
    }
    
    this.show=show;
    function show(){        
        updateDialog();        
        this.dialog.show();
        this.isOpen = true;           
    }
    
    this.hide=hide;
    function hide(){        
        this.dialog.hide();
        this.isOpen = false;
        //alert('hide: ' +  this.isOpen);
    }   
    
    this.updateDialog=updateDialog;
    function updateDialog(){        
        //rcUpdateClient();
        alert('updateDialog: ' +  uText);
        ercUpdateString(uText);
    }   
    
}
