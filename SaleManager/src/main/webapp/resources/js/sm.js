onload = function() {        
    //jDlgContact = new MyDialog(wDlgContact);    
};

function MyDialog(iDialog){
    var dialog = iDialog;
    var isOpen = false;    
    
    this.onHide=onHide;
    function onHide(){
        this.isOpen = false;
        //alert('onHide: ' +  this.isOpen);
    }
    
    this.show=show;
    function show(){                
        dialog.show();
        isOpen = true;           
    }
    
    this.hide=hide;
    function hide(){        
        dialog.hide();
        isOpen = false;
    }   
}