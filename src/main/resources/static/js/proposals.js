document.addEventListener("DOMContentLoaded", () => {

    // Confirmation dialogs
    document.querySelectorAll("[data-proposal-confirm]").forEach(button => {

        button.addEventListener("click", (event) => {

            const message = button.dataset.proposalConfirm;

            if (!confirm(message)) {
                event.preventDefault();
            }

        });

    });


    // Prevent double submission
    document.querySelectorAll("[data-proposal-action]").forEach(button => {

        const form = button.closest("form");

        if (!form) {
            return;
        }

        form.addEventListener("submit", () => {

            button.disabled = true;

            const originalText = button.textContent;

            button.textContent = "Processing...";

            setTimeout(() => {
                button.textContent = originalText;
            }, 5000);

        });

    });

});