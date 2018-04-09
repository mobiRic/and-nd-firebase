const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

/**
 * Replaces keywords with emoji in the "text" key of messages
 * pushed to /messages
 *
 * @type {CloudFunction<Change<DataSnapshot>>}
 */
exports.emojify =
  functions.database
    .ref('/messages/{pushId}/text')
    .onCreate((snapshot) => {

        const original = snapshot.val();
        const emojiText = emojifyText(original);
        if (original === emojiText) {
          return null;
        }

        return snapshot.ref.set(emojiText);
      }
    );

/**
 * Replaces certain text strings with emoji icons.
 * @param text message entered by the user
 * @return string message with embedded emoji icons
 */
function emojifyText(text) {
  return text
    .replace(/\blol\b/ig, "😂")
    .replace(/\bcat\b/ig, "😸")
    .toString();
}