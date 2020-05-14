/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.codelab.friendlychat

//import androidx.test.filters.LargeTest
//import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
//import androidx.test.rule.ActivityTestRule
//import org.junit.Rule
//import org.junit.runner.RunWith

class FriendlyMessage {
    private var id: String? = null
    private var text: String? = null
    private var name: String? = null
    private var photoUrl: String? = null
    private var imageUrl: String? = null

    constructor() {}
    constructor(text: String?, name: String?, photoUrl: String?, imageUrl: String?) {
        this.text = text
        this.name = name
        this.photoUrl = photoUrl
        this.imageUrl = imageUrl
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun setText(text: String?) {
        this.text = text
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPhotoUrl(): String? {
        return photoUrl
    }

    fun getText(): String? {
        return text
    }

    fun setPhotoUrl(photoUrl: String?) {
        this.photoUrl = photoUrl
    }

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
    }
}