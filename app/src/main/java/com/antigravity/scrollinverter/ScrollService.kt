package com.antigravity.scrollinverter

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.util.Log

class ScrollService : AccessibilityService() {

    private val TAG = "ScrollService"
    private var lastScrollTime = 0L
    private val SCROLL_DEBOUNCE_MS = 50L 
    private var lastAutoScrollTime = 0L
    private val AUTO_SCROLL_WINDOW_MS = 250L // Ignore events after we triggered a scroll

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        
        // We only care about scroll events
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
            
            val currentTime = System.currentTimeMillis()
            
            // 1. Debounce rapid natural events
            if (currentTime - lastScrollTime < SCROLL_DEBOUNCE_MS) {
                return
            }

            // 2. Loop prevention: Ignore if we just triggered a scroll recently
            if (currentTime - lastAutoScrollTime < AUTO_SCROLL_WINDOW_MS) {
                return
            }
            
            // Check for scroll source if possible, or just assume it's relevant.
            // Requires API 28+ for getScrollDeltaY
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                val deltaY = event.scrollDeltaY
                if (deltaY == 0) return // No vertical change or indeterminate

                lastScrollTime = currentTime
                
                // Find the source node
                val sourceNode = event.source ?: return
                
                var actionPerformed = false
                if (deltaY > 0) {
                    // Scrolled Forward (Down). We want Backward (Up).
                     actionPerformed = performScroll(sourceNode, AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
                } else {
                    // Scrolled Backward (Up). We want Forward (Down).
                    actionPerformed = performScroll(sourceNode, AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                }
                
                if (actionPerformed) {
                    lastAutoScrollTime = System.currentTimeMillis()
                }
            }
        }
    }

    private fun performScroll(node: AccessibilityNodeInfo, action: Int): Boolean {
        if (node.isScrollable) {
            return node.performAction(action)
        } else {
            // Try parent
            val parent = node.parent
            if (parent != null && parent.isScrollable) {
                return parent.performAction(action)
            }
        }
        return false
    }
    
    // Let's implement the skeleton first.
    override fun onInterrupt() {
        Log.d(TAG, "Service Interrupted")
    }
}
