document.addEventListener('DOMContentLoaded', function (e){

    const page = document.body.dataset.page;

    switch (page) {
        case 'admin':
            import('../pages/admin.js');
            break;
        case 'create-post':
            import('../pages/create-post.js');
            break;
        case 'update-post':
            import('../pages/update-post');
            break;
        case 'create-tag':
            import('../pages/create-tag.js');
            break;

    }
});