// ===============================
// INIT
// ===============================

const REFRESH_INTERVAL = 600000; // 10 minutes
const ICON_LOCATION = 'assets/npc.png'

let dataSources = {};
let enabledSources = {};

chrome.runtime.onMessage.addListener((msg) => {
    if (msg.type === "TOGGLE_SOURCE") {
        enabledSources[msg.key] = msg.value;

        clearHighlights();
        initialScan();
    }
});

async function init() {
    await loadProfiles();
    await loadEnabledSources();

    initialScan();

    observer.observe(document.body, {
        childList: true,
        subtree: true
    });

    setInterval(refreshProfiles, REFRESH_INTERVAL);
}

// ===============================
// LOAD PROFILES FROM STATIC FILE
// ===============================

let highlightedProfiles = new Set();

async function loadProfiles() {
    for (const [key, source] of Object.entries(DATA_SOURCES)) {
        const fileUrl = chrome.runtime.getURL(source.path);
        console.log("-------------");
        console.log(key);
        console.log(source);
        console.log(fileUrl);
        console.log("-------------");
        const res = await fetch(fileUrl);
        const data = await res.json();

        dataSources[key] = new Set(data.map(p => p.profileUri));
    }
}

function loadEnabledSources() {
    return new Promise(resolve => {
        chrome.storage.local.get(Object.keys(DATA_SOURCES), result => {
            Object.keys(DATA_SOURCES).forEach(key => {
                enabledSources[key] = result[key] ?? true;
            });
            resolve();
        });
    });
}

// ===============================
// INITIAL SCAN
// ===============================

// Function to highlight matching elements
function highlightComments(root = document) {
    const commentElements = document.querySelectorAll('div[role=article]:last-child');
    const toHighlight = [...commentElements];

    toHighlight.forEach(el => {
        const profileLink = el.querySelector('a'); // Assuming the profile link is in an <a> tag
        const profileIdMatch = profileLink ? profileLink.href.match(/profile.php\?id=(\d+)/) : null;
        const vanityMatch = profileLink ? profileLink.href.match(/facebook\.com\/([^\/?]+)/) : null;

        let profileIdentifier = null;


        if (profileIdMatch) {
            profileIdentifier = profileIdMatch[1];
        }

        else if (vanityMatch) {
            profileIdentifier = vanityMatch[1];
        }

        if (profileIdentifier && isProfileHighlighted(profileIdentifier)) {
            if (!el.classList.contains('comment-highlighted')) {
                el.style.backgroundColor = 'rgba(255, 137, 0, 0.6)';
                el.style.border = '2px solid orange';
                el.style.borderRadius = '6px';
                el.style.padding = '4px';
                el.classList.add('comment-highlighted');  // Mark as processed
				// Create icon
				const icon = document.createElement("img");
				icon.src = chrome.runtime.getURL(ICON_LOCATION);
				icon.classList.add("npc-icon");

				icon.style.position = "absolute";
				icon.style.top = "4px";
				icon.style.right = "4px";
				icon.style.width = "50px";
				icon.style.height = "60px";
				icon.style.opacity = "0.9";
				icon.style.pointerEvents = "none"; // prevents click interference

				// Ensure container can anchor absolute children
				el.style.position = "relative";

				el.appendChild(icon);
         }
    }
    });
}

function isProfileHighlighted(profileIdentifier) {
    return Object.keys(dataSources).some(key =>
        enabledSources[key] &&
        dataSources[key]?.has(profileIdentifier)
    );
}

function highlightFollowers(root = document){
    const followerElements = document.querySelectorAll('div:has(a[href$="followers"]) + div + div:has(a[href*="facebook.com/"][tabindex="0"]) > div');

   followerElements.forEach(el => {
        const profileLink = el.querySelector('a'); // Assuming the profile link is in an <a> tag
        const profileIdMatch = profileLink ? profileLink.href.match(/profile.php\?id=(\d+)/) : null;
        const vanityMatch = profileLink ? profileLink.href.match(/facebook\.com\/([^\/?]+)/) : null;

        let profileIdentifier = null;

        if (profileIdMatch) {
            profileIdentifier = profileIdMatch[1];
        }

        else if (vanityMatch) {
            profileIdentifier = vanityMatch[1];
        }
        if (profileIdentifier && isProfileHighlighted(profileIdentifier)) {
            if (!el.classList.contains('comment-highlighted')) {
                el.style.backgroundColor = 'rgba(255, 137, 0, 0.6)';
                el.style.border = '2px solid orange';
                el.style.borderRadius = '6px';
                el.style.padding = '4px';
                el.classList.add('comment-highlighted');  // Mark as processed
				// Create icon
				const icon = document.createElement("img");
				icon.src = chrome.runtime.getURL(ICON_LOCATION);
				icon.classList.add("npc-icon");

				icon.style.position = "absolute";
				icon.style.top = "20%";
				icon.style.right = "20%";
				icon.style.width = "50px";
				icon.style.height = "60px";
				icon.style.opacity = "0.9";
				icon.style.pointerEvents = "none"; // prevents click interference

				// Ensure container can anchor absolute children
				el.style.position = "relative";

				el.appendChild(icon);
         }
     }
    }
    );
}

function highlightReactions(root = document){
    const reactionElements = getElementsByXPath('//div[@role="dialog"]//a[contains(@href, "facebook.com")]/parent::span/parent::div/parent::span/parent::div/parent::div/parent::div/parent::div/parent::div',
                                                document);
   reactionElements.forEach(el => {
        const profileLink = el.querySelector('a'); // Assuming the profile link is in an <a> tag
        const profileIdMatch = profileLink ? profileLink.href.match(/profile.php\?id=(\d+)/) : null;
        const vanityMatch = profileLink ? profileLink.href.match(/facebook\.com\/([^\/?]+)/) : null;

        let profileIdentifier = null;

        if (profileIdMatch) {
            profileIdentifier = profileIdMatch[1];
        }

        else if (vanityMatch) {
            profileIdentifier = vanityMatch[1];
        }

        if (profileIdentifier && isProfileHighlighted(profileIdentifier)) {
            if (!el.classList.contains('comment-highlighted')) {
                el.style.backgroundColor = 'rgba(255, 137, 0, 0.6)';
                el.style.border = '2px solid orange';
                el.style.borderRadius = '6px';
                el.style.padding = '4px';
                el.classList.add('comment-highlighted');  // Mark as processed
				// Create icon
				const icon = document.createElement("img");
				icon.src = chrome.runtime.getURL(ICON_LOCATION);
				icon.classList.add("npc-icon");

				icon.style.position = "absolute";
				icon.style.top = "15%";
				icon.style.right = "25%";
				icon.style.width = "34px";
				icon.style.height = "40px";
				icon.style.opacity = "0.9";
				icon.style.pointerEvents = "none"; // prevents click interference

				// Ensure container can anchor absolute children
				el.style.position = "relative";

				if (!el.querySelector('.npc-icon')) {
                    el.appendChild(icon);
                }
         }
     }
    }
    );
}

// ===============================
// XPATH HELPER
// ===============================

function getElementsByXPath(xpath, parent)
{
    let results = [];
    let query = document.evaluate(xpath, parent || document,
        null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
    for (let i = 0, length = query.snapshotLength; i < length; ++i) {
        results.push(query.snapshotItem(i));
    }
    return results;
}

function initialScan() {
    highlightComments();
    highlightFollowers();
    highlightReactions();
}

// ===============================
// MUTATION OBSERVER
// ===============================

const observer = new MutationObserver(mutations => {
    mutations.forEach(mutation => {
        mutation.addedNodes.forEach(node => {
            if (node.nodeType === 1) { 
                highlightComments(node);
                highlightFollowers(node);
                highlightReactions(node);
            }
        });
    });
});

function clearHighlights() {
    document.querySelectorAll('.comment-highlighted').forEach(el => {
        el.style = "";
        el.classList.remove('comment-highlighted');
        // remove injected icons
        const icons = el.querySelectorAll('.npc-icon');
        icons.forEach(icon => icon.remove());
    });
}

// ===============================
// PROFILE REFRESH
// ===============================

async function refreshProfiles() {
    console.log("Refreshing profiles...");
    await loadProfiles();
}


init();