let tagLabels = document.querySelectorAll('.tag-label');
if(tagLabels.length > 0){

    tagLabels.forEach(label => {

        const checkbox = label.previousElementSibling;

        function updateLabelStyle() {
            if (checkbox.checked) {
                label.classList.add('btn-primary');
                label.classList.remove('btn-outline-primary');
            } else {
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

}
