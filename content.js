// ===============================
// INIT
// ===============================

const PROFILE_LOCATION='assets/profiles.json'
const REFRESH_INTERVAL = 600000; // 10 minutes
const ICON_LOCATION = 'assets/npc.png'

async function init() {
    await loadProfiles();

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
    try {
        const fileUrl = chrome.runtime.getURL(PROFILE_LOCATION); 
        const response = await fetch(fileUrl);
        const data = await response.json();

        highlightedProfiles = new Set(data.map(profile => profile.profileUri));
        console.log("Loaded highlighted profiles:", highlightedProfiles);
    } catch (error) {
        console.error("Error loading profiles from the static file:", error);
    }
}

// ===============================
// INITIAL SCAN
// ===============================

// Function to highlight matching elements
function highlightComments(root = document) {
    const commentElements = document.querySelectorAll('[aria-label*="Comment by"]:last-child');
    const replyElements = document.querySelectorAll('[aria-label*="Reply by"]:last-child');
    const toHighlight = [...commentElements, ...replyElements];

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

        if (profileIdentifier && highlightedProfiles.has(profileIdentifier)) {
            if (!el.classList.contains('comment-highlighted')) {
                el.style.backgroundColor = 'rgba(255, 137, 0, 0.6)';
                el.style.border = '2px solid orange';
                el.style.borderRadius = '6px';
                el.style.padding = '4px';
                el.classList.add('comment-highlighted');  // Mark as processed
				// Create icon
				const icon = document.createElement("img");
				icon.src = chrome.runtime.getURL(ICON_LOCATION);

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

function highlightFollowers(root = document){
    const followerElements = document.querySelectorAll('div:has(a[href$="/followers"]) + div + div:has(a[href*="facebook.com/"][tabindex="0"]) > div');

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

        if (profileIdentifier && highlightedProfiles.has(profileIdentifier)) {
            if (!el.classList.contains('comment-highlighted')) {
                el.style.backgroundColor = 'rgba(255, 137, 0, 0.6)';
                el.style.border = '2px solid orange';
                el.style.borderRadius = '6px';
                el.style.padding = '4px';
                el.classList.add('comment-highlighted');  // Mark as processed
				// Create icon
				const icon = document.createElement("img");
				icon.src = chrome.runtime.getURL(ICON_LOCATION);

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


function initialScan() {
    highlightComments();
    highlightFollowers();
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
            }
        });
    });
});

// ===============================
// PROFILE REFRESH
// ===============================

async function refreshProfiles() {
    console.log("Refreshing profiles...");
    await loadProfiles();
}


init();