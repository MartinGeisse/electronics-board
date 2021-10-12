import {BoardPage} from "./components/BoardPage";
import {MousePositionContextProvider} from "./util/MousePositionContext";

function App() {
    return <MousePositionContextProvider>
        <BoardPage/>
    </MousePositionContextProvider>;
}

export default App;
