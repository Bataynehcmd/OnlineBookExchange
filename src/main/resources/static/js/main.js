document.addEventListener("DOMContentLoaded", () => {

    // Confirmation dialogs
    document.querySelectorAll("[data-confirm]").forEach(button => {

        button.addEventListener("click", (event) => {

            const message = button.dataset.confirm;

            if (!confirm(message)) {
                event.preventDefault();
            }

        });

    });


    // Auto-hide flash messages
    document.querySelectorAll("[data-auto-hide]").forEach(element => {

        setTimeout(() => {
            element.style.transition = "opacity 0.5s ease";
            element.style.opacity = "0";

            setTimeout(() => {
                element.remove();
            }, 500);

        }, 4000);

    });

});