document.querySelectorAll('.tag-label').forEach(label => {

    const checkbox = label.previousElementSibling;

    function updateLabelStyle() {
        if (checkbox.checked) {
            console.log('checked')
            label.classList.add('btn-primary');
            label.classList.remove('btn-outline-primary');
        } else {
            console.log('unchecked')
            label.classList.remove('btn-primary');
            label.classList.add('btn-outline-primary');
        }
    }

    label.addEventListener('mouseover', function() {
        if (!this.previousElementSibling.checked) {
            this.classList.add('btn-primary');
            this.classList.remove('btn-outline-primary');
        }
    });

    label.addEventListener('mouseout', function() {
        if (!this.previousElementSibling.checked) {
            this.classList.remove('btn-primary');
            this.classList.add('btn-outline-primary');
        }
    });

    label.addEventListener('click', function() {
        setTimeout(updateLabelStyle, 10);
    });
});

let successMsgBtnClose = document.querySelector('#successMessage').querySelector('.btn-close');
successMsgBtnClose.addEventListener('click', function (event){
    event.preventDefault();
    this.parentElement.remove();
})