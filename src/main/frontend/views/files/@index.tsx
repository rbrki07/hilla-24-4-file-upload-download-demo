import { AutoGrid, AutoGridRef } from '@vaadin/hilla-react-crud';
import {
  ContextMenu,
  ContextMenuElement,
  ContextMenuRendererContext,
  Item,
  ListBox,
  Upload,
  VerticalLayout,
} from '@vaadin/react-components';
import FileModel from 'Frontend/generated/com/example/application/entities/FileModel';
import { FileBrowserCallable } from 'Frontend/generated/endpoints';
import { useRef } from 'react';

type MenuProps = Readonly<{
  context: ContextMenuRendererContext;
  original: ContextMenuElement;
}>;

export default function Files() {
  const autoGridRef = useRef<AutoGridRef>(null);

  const renderMenu = ({ context }: MenuProps) => {
    const grid = autoGridRef.current?.grid;
    if (!grid) {
      return null;
    }
    const { sourceEvent } = context.detail as { sourceEvent: Event };
    const eventContext = grid.getEventContext(sourceEvent);
    const file = eventContext.item;
    if (!file) {
      return null;
    }

    return (
      <ListBox>
        <Item
          onClick={async () => {
            if (file?.id) {
              try {
                await FileBrowserCallable.delete(file.id);
                if (autoGridRef.current) {
                  autoGridRef.current.refresh();
                }
              } catch (e) {
                alert(e);
              }
            }
          }}
        >
          Delete
        </Item>
        <Item
          onClick={() => {
            window.open(`/api/files/${file.filename}`, '_blank');
          }}
        >
          Download
        </Item>
      </ListBox>
    );
  };

  return (
    <VerticalLayout className='h-full'>
      <ContextMenu renderer={renderMenu} className='w-full'>
        <AutoGrid ref={autoGridRef} model={FileModel} service={FileBrowserCallable} />
      </ContextMenu>
      <Upload
        accept='application/pdf,.pdf'
        maxFiles={1}
        maxFileSize={10485760} // 10MB
        target='/api/files'
        className='w-full mt-l'
        onUploadSuccess={() => {
          autoGridRef.current?.refresh();
        }}
      />
    </VerticalLayout>
  );
}
