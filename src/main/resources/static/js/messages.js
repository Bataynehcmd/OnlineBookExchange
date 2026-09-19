document.addEventListener("DOMContentLoaded", () => {

    const messageForm = document.querySelector("#messageForm");
    const messageInput = document.querySelector("#messageText");
    const characterCount = document.querySelector("#characterCount");

    if (!messageForm || !messageInput) {
        return;
    }

    // Character counter
    if (characterCount) {

        const updateCounter = () => {
            characterCount.textContent =
                `${messageInput.value.length} characters`;
        };

        messageInput.addEventListener("input", updateCounter);

        updateCounter();
    }


    // Validate message before sending
    messageForm.addEventListener("submit", (event) => {

        const message = messageInput.value.trim();

        if (message.length === 0) {
            event.preventDefault();

            alert("Please enter a message.");

            messageInput.focus();

            return;
        }


        // Prevent double submission
        const submitButton =
            messageForm.querySelector('button[type="submit"]');

        if (submitButton) {
            submitButton.disabled = true;
            submitButton.textContent = "Sending...";
        }

    });

});