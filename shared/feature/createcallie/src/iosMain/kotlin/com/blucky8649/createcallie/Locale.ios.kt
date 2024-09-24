package com.blucky8649.createcallie

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual val defaultLocale: String get() = NSLocale.currentLocale.languageCode