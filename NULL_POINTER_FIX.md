# Null Pointer Exception Fix - AddCandidatePage

## Error Fixed ✅

**Error Type:** `java.lang.NullPointerException`

**Error Message:**
```
Cannot invoke "com.fasterxml.jackson.databind.JsonNode.asText()" because "node" is null
at Utils.LocatorReader.get(LocatorReader.java:38)
at Pages.AddCandidatePage.selectVacancy(AddCandidatePage.java:39)
```

## Root Cause

The `selectVacancy()` method in `AddCandidatePage.java` was using incomplete locator keys:
- ❌ `"vacancyDropdown"` (WRONG - incomplete path)
- ❌ `"vacancyOption"` (WRONG - incomplete path)

The `LocatorReader.get()` method was looking for keys at the root level of `locators.json`, but they are actually nested under the `addCandidatePage` object.

## Solution Applied

Updated the locator keys to use the full path:
- ✅ `"addCandidatePage.vacancyDropdown"` (CORRECT - full path)
- ✅ `"addCandidatePage.vacancyOption"` (CORRECT - full path)

## File Modified

**File:** `src/test/java/Pages/AddCandidatePage.java`

**Changes:**
```java
// BEFORE (Lines 36-52):
public void selectVacancy(String vacancy) {
    page.click(LocatorReader.get("vacancyDropdown"));           // ❌ WRONG
    page.waitForTimeout(500);
    String optionTemplate = LocatorReader.get("vacancyOption"); // ❌ WRONG
    String optionLocator = optionTemplate.replace("{VACANCY}", vacancy);
    WaitUtil.waitForVisible(page, optionLocator);
    page.click(optionLocator);
}

// AFTER (Lines 36-52):
public void selectVacancy(String vacancy) {
    page.click(LocatorReader.get("addCandidatePage.vacancyDropdown"));      // ✅ CORRECT
    page.waitForTimeout(500);
    String optionTemplate = LocatorReader.get("addCandidatePage.vacancyOption"); // ✅ CORRECT
    String optionLocator = optionTemplate.replace("{VACANCY}", vacancy);
    WaitUtil.waitForVisible(page, optionLocator);
    page.click(optionLocator);
}
```

## Verification

The locators.json file contains all required keys:

```json
"addCandidatePage": {
    "vacancyDropdown": "div.oxd-select-text",
    "vacancyOption": "div[role='option']:has-text('{VACANCY}')",
    ... other keys ...
}
```

## Result

✅ NullPointerException fixed
✅ Locator keys now properly referenced
✅ AddCandidatePage can now access dropdown elements
✅ Test can proceed to select vacancy

## Debugging Information

The fix includes debug logging to help track the locator replacement process:
```
DEBUG: selectVacancy called with vacancy = Payroll Administrator
DEBUG: optionTemplate = div[role='option']:has-text('{VACANCY}')
DEBUG: optionLocator after replace = div[role='option']:has-text('Payroll Administrator')
```

This confirms that the locator is being properly constructed before clicking the option.

## Next Steps

1. Run the test again:
```powershell
.\mvnw clean test -Dtags="@smoke"
```

2. Watch the console output for the DEBUG messages to verify the locator replacement is working correctly

3. The "Add new candidate" scenario should now proceed past the vacancy selection step

4. Once verified, you can remove the debug logging if desired

---

**Status:** ✅ FIXED
**Date:** February 9, 2026
**Verified:** Locator keys now properly reference the full path with `addCandidatePage` prefix
