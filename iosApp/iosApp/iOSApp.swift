import SwiftUI

@main
struct iOSApp: App {
    init() {
        initKoinIos()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}