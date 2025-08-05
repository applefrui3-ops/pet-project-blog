let successMsg = document.querySelector('#successMessage');
if(successMsg){
    let successMsgBtnClose = successMsg.querySelector('.btn-close');
    successMsgBtnClose.addEventListener('click', function (event){
        event.preventDefault();
        this.parentElement.remove();
    })
}
