const container = document.getElementById("list");
const keys = Object.keys(DATA_SOURCES);

// Load saved state
chrome.storage.local.get(keys, (saved) => {

    keys.forEach(key => {
        const source = DATA_SOURCES[key];
        const enabled = saved[key] ?? true;

        const row = document.createElement("div");
        row.className = "item";

        const img = document.createElement("img");
        img.src = chrome.runtime.getURL(source.icon);
        img.className = "icon";

        const label = document.createElement("span");
        label.textContent = source.name;

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = enabled;

        checkbox.addEventListener("change", () => {
            const value = checkbox.checked;

            chrome.storage.local.set({ [key]: value });

            chrome.tabs.query({ active: true, currentWindow: true }, tabs => {
                chrome.tabs.sendMessage(tabs[0].id, {
                    type: "TOGGLE_SOURCE",
                    key,
                    value
                });
            });
        });

        row.appendChild(img);
        row.appendChild(label);
        row.appendChild(checkbox);
        container.appendChild(row);
    });
});