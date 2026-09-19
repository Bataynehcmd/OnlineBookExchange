document.addEventListener("DOMContentLoaded", () => {

    // Show price only for SELL listings
    const listingTypes = document.querySelectorAll(
        'input[name="listingType"]'
    );

    const priceInput = document.querySelector(
        'input[name="price"]'
    );

    const priceContainer = priceInput?.parentElement;

    function updatePriceField() {

        const selectedType = document.querySelector(
            'input[name="listingType"]:checked'
        );

        if (!selectedType) {
            return;
        }

        if (selectedType.value === "SELL") {
            priceContainer.style.display = "block";
            priceInput.required = true;
        } else {
            priceContainer.style.display = "none";
            priceInput.required = false;
            priceInput.value = "";
        }
    }

    listingTypes.forEach(type => {
        type.addEventListener("change", updatePriceField);
    });

    updatePriceField();


    // Image validation and preview
    const imageInput = document.querySelector(
        'input[name="image"]'
    );

    if (imageInput) {

        imageInput.addEventListener("change", () => {

            const file = imageInput.files[0];

            if (!file) {
                return;
            }

            // Maximum 5MB
            const maxSize = 5 * 1024 * 1024;

            if (file.size > maxSize) {

                alert("Image size must not exceed 5MB.");

                imageInput.value = "";

                return;
            }

            // Make sure the selected file is an image
            if (!file.type.startsWith("image/")) {

                alert("Please select a valid image.");

                imageInput.value = "";

                return;
            }

        });

    }

});