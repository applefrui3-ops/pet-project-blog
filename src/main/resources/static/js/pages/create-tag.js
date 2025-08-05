document.querySelector('#add-tag-btn').addEventListener('click', function (e){
   e.preventDefault();
   addTagField();
});
document.querySelector('#remove-tag-btn').addEventListener('click', function (e){
    e.preventDefault();
    removeTagField(this);
});

loadExistingTags();



function addTagField() {
    const container = document.getElementById('tagsContainer');
    const newGroup = document.createElement('div');
    newGroup.className = 'input-group mb-3 tag-input-group';
    newGroup.innerHTML = `
                <input type="text" class="form-control" name="tags[]" placeholder="Input tag's name" required>
                <button class="btn btn-outline-danger" type="button">×</button>
            `;
    let appendedChild = container.appendChild(newGroup);
    appendedChild.querySelector('button').addEventListener('click', function (e){
        e.preventDefault();
        removeTagField(this);
    })
}


function removeTagField(button) {
    const groups = document.querySelectorAll('.tag-input-group');
    if (groups.length > 1) {
        button.closest('.tag-input-group').remove();
    } else {
        button.closest('.tag-input-group').querySelector('input').value = '';
    }
}


function loadExistingTags() {
    axios.get('/api/tags')
        .then(response => {
            const tagsContainer = document.getElementById('existingTags');
            tagsContainer.innerHTML = '';

            if (response.data.length === 0) {
                tagsContainer.innerHTML = '<p>Пока нет добавленных тегов</p>';
                return;
            }

            response.data.forEach(tag => {
                const tagElement = document.createElement('span');
                tagElement.className = 'badge bg-primary tag-badge me-2 mb-2';
                tagElement.innerHTML = `
                            ${tag.name}
                            <button class="btn btn-sm btn-outline-light ms-2">
                                ×
                            </button>
                        `;
                let appendedChild = tagsContainer.appendChild(tagElement);
                console.log(appendedChild);
                appendedChild.addEventListener('click', function (e){
                    e.preventDefault();
                    showDeleteModal(tag.id, tag.name);
                })
            });
        })
        .catch(error => {
            console.error('Ошибка загрузки тегов:', error);
            document.getElementById('existingTags').innerHTML =
                '<div class="alert alert-danger">Ошибка загрузки тегов</div>';
        });
}


let tagToDelete = null;
function showDeleteModal(tagId, tagName) {
    tagToDelete = tagId;
    const modal = new bootstrap.Modal(document.getElementById('deleteTagModal'));
    modal.show();
}

document.getElementById('confirmDelete').addEventListener('click', function() {
    if (tagToDelete) {
        const csrfToken = document.querySelector('#tagsForm [name=_csrf]').value;
        axios.delete(`/api/tags/${tagToDelete}`, {
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
            .then(() => {
                loadExistingTags();
                bootstrap.Modal.getInstance(document.getElementById('deleteTagModal')).hide();
            })
            .catch(error => {
                console.error('Ошибка удаления тега:', error);
                alert('Ошибка при удалении тега');
            });
    }
});





    //form handler
    document.getElementById('tagsForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const csrfToken = document.querySelector('#tagsForm [name=_csrf]').value;
        const formData = new FormData(this);
        const tags = formData.getAll('tags[]').filter(tag => tag.trim() !== '');

        if (tags.length === 0) {
            alert('Добавьте хотя бы один тег');
            return;
        }

        axios.post(this.action, { tags }, {
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
            .then(response => {
                alert('Теги успешно добавлены');
                this.reset();
                const groups = document.querySelectorAll('.tag-input-group');
                for (let i = 1; i < groups.length; i++) {
                    groups[i].remove();
                }
                loadExistingTags();
            })
            .catch(error => {
                console.error('Ошибка добавления тегов:', error);
                alert('Ошибка при добавлении тегов');
            });
    });
