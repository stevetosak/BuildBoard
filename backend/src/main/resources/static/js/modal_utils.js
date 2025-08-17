const modalConts = []
document.addEventListener("keydown", ev => {
    if (ev.key !== 'Escape') return null;
    modalConts.forEach(cont => {
        if (cont.classList.contains("show")) {
            const close = cont.querySelector(".btn-close")
            close.click()
        }
    })
})