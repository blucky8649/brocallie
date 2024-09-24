import SwiftUI
import shared

struct MainView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> some UIViewController {
        BcApp_iosKt.BcViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct ContentView: View {
	var body: some View {
        MainView()
            .ignoresSafeArea(.container)
            .navigationBarBackButtonHidden()
	}
}

#Preview {
    ContentView()
}

/**
 * Swipe - Back 강제 활성화
 */
extension UINavigationController: UIGestureRecognizerDelegate {
    override open func viewDidLoad() {
        super.viewDidLoad()
        interactivePopGestureRecognizer?.delegate = self
    }

    public func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return viewControllers.count > 1
    }
}
